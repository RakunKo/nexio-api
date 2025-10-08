package nexio.api_server.infrastructure.security.resolver

import kotlinx.coroutines.reactor.mono
import nexio.api_server.application.common.exception.ApiException
import nexio.api_server.application.common.status.CommonErrorStatus
import nexio.api_server.application.service.UserIdentifier
import nexio.api_server.application.service.UserService
import nexio.api_server.application.validator.annotations.AuthUser
import nexio.api_server.domain.User
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import org.springframework.security.oauth2.jwt.Jwt
import java.util.UUID


@Component
class AuthUserResolver(
        private val userService: UserService
): HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.hasParameterAnnotation(AuthUser::class.java) && parameter.parameterType == User::class.java

    override fun resolveArgument(
            parameter: MethodParameter,
            bindingContext: BindingContext,
            exchange: ServerWebExchange): Mono<Any> = ReactiveSecurityContextHolder.getContext()
                    .flatMap { context ->
                        val jwt = context.authentication.credentials as? Jwt
                        val userId = jwt?.claims?.get("id")?.toString() ?. let { UUID.fromString(it) }
                        if (userId != null) {
                            mono { userService.getUser(UserIdentifier.ById(userId)) }
                        } else Mono.error(ApiException(CommonErrorStatus.UNAUTHORIZED))
                    }
}