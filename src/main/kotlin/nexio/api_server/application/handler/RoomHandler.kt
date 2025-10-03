package nexio.api_server.application.handler

import nexio.api_server.application.service.RoomService
import nexio.api_server.application.validator.RoomValidator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class RoomHandler(
        private val roomValidator: RoomValidator,
        private val roomService: RoomService
) {
    suspend fun getRooms(request: ServerRequest): ServerResponse = handleRoute(
            request = request,
            supplier = { roomService.getRoomsService() }
    )

    suspend fun getRoom(request: ServerRequest): ServerResponse = handleRoute(
            request = request,
            validator = { roomValidator.validateGetRooms(request) },
            supplier = { id -> roomService.getRoomService(id) }
    )
}