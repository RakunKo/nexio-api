package nexio.api_server.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info


@Configuration
class SwaggerConfig {
    @Bean
    fun apiDocs(): OpenAPI = OpenAPI()
            .info(
                    Info()
                            .title("Nexio Api Docs")
                            .version("v0.0.1")
                            .description("Nexio API documentation using OpenAPI 3")
            )
}