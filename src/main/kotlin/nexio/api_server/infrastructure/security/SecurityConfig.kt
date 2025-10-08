package nexio.api_server.infrastructure.security

import kotlinx.coroutines.reactor.mono
import nexio.api_server.application.common.code.BaseErrorCode
import nexio.api_server.application.common.status.CommonErrorStatus
import nexio.api_server.application.common.utils.JsonUtils
import nexio.api_server.application.service.UserIdentifier
import nexio.api_server.application.service.UserService
import nexio.api_server.domain.enums.Roles
import nexio.api_server.infrastructure.security.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
        private val jwtProvider: JwtProvider,
        private val userService: UserService
) {
    companion object {
        private val PUBLIC_PATHS = arrayOf(
                "/login/**",
                "/oauth2/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/webjars/**"
        )
    }
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http.apply {
        csrf{ csrf -> csrf.disable() }
        authorizeExchange { exchanges ->
            exchanges
                    .pathMatchers(*PUBLIC_PATHS).permitAll()
                    .pathMatchers("/api/v1/users/signup").permitAll()
                    .pathMatchers("/api/v1/**").authenticated()
        }
        oauth2Login { oauth2 -> oauth2.applyOauth2Handler() }
        oauth2ResourceServer { oauth2 ->
            oauth2.jwt{ jwtSpec -> jwtSpec.jwtDecoder(jwtProvider.getJwtDecoder()) }
        }
        applyJsonExceptionHandling()
    }.build()

    private fun ServerHttpSecurity.OAuth2LoginSpec.applyOauth2Handler() {
        this.authenticationSuccessHandler { webFilterExchange, authentication ->
            val exchange = webFilterExchange.exchange
            val oauthToken = authentication as OAuth2AuthenticationToken

            val user = oauthToken.principal as DefaultOidcUser
            val subject = user.getAttribute<String>("sub") ?: user.name

            Mono.defer {
                mono { userService.getUser(UserIdentifier.ByProvider(subject)) }
            }.flatMap { foundUser ->
                jwtProvider.generateJwtTokenMono(subject, foundUser)
            }.flatMap { jwt ->
                val response = mapOf(
                        "accessToken" to jwt.accessToken,
                        "refreshToken" to jwt.refreshToken,
                        "tokenType" to "Bearer",
                        "expiresIn" to 3600
                )

                val buffer = exchange.response.bufferFactory()
                        .wrap(JsonUtils.objectMapper.writeValueAsBytes(response))

                exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                exchange.response.statusCode = HttpStatus.OK
                exchange.response.writeWith(Mono.just(buffer))
            }.switchIfEmpty { exchange.unauthorized() }
        }
        this.authenticationFailureHandler { filterExchange, _ -> filterExchange.exchange.unauthorized() }
    }


    private fun ServerHttpSecurity.applyJsonExceptionHandling(): ServerHttpSecurity = this.exceptionHandling{ ex ->
        ex.authenticationEntryPoint { exchange, _ -> exchange.unauthorized() }
        ex.accessDeniedHandler{ exchange, _ -> exchange.forbidden() }
    }

    private fun ServerWebExchange.unauthorized(): Mono<Void> = this.toErrorBody(CommonErrorStatus.UNAUTHORIZED)
    private fun ServerWebExchange.forbidden(): Mono<Void> = this.toErrorBody(CommonErrorStatus.FORBIDDEN_ROLE)

    private fun ServerWebExchange.toErrorBody(code: BaseErrorCode): Mono<Void> {
        val body = mapOf(
                "status" to code.status.value(),
                "message" to code.message
        )
        val buffer = response.bufferFactory().wrap(JsonUtils.objectMapper.writeValueAsBytes(body))
        response.statusCode = code.status
        response.headers.contentType = MediaType.APPLICATION_JSON
        return response.writeWith(Mono.just(buffer))
    }
}