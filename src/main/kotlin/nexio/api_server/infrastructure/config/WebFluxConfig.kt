package nexio.api_server.infrastructure.config

import nexio.api_server.infrastructure.security.resolver.AuthUserResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

@Configuration
class WebFluxConfig(
        private val authUserResolver: AuthUserResolver
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(authUserResolver)
    }
}