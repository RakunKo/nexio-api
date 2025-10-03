package nexio.api_server.application.validator

import nexio.api_server.application.utils.pathString
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class RoomValidator {
    suspend fun validateGetRooms(request: ServerRequest): String = request.pathString("id")
}