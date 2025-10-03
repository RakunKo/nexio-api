package nexio.api_server.infrastructure.router

import com.nexio.ApiVersion
import nexio.api_server.application.handler.RoomHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RoomRouter(private val handler: RoomHandler) {
    @Bean
    fun roomRouter(): RouterFunction<ServerResponse> = coRouter {
        "/api/${ApiVersion.API_VERSION}/rooms".nest {
            GET("/", handler::getRooms)
            GET("{id}", handler::getRoom)
        }
    }
}