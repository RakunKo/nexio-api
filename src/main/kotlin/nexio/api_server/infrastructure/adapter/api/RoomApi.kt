package nexio.api_server.infrastructure.adapter.api

import nexio.api_server.application.handler.handleApi
import nexio.api_server.application.service.RoomService
import nexio.api_server.application.validator.RoomValidator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rooms")
class RoomApi (
        private val roomValidator: RoomValidator,
        private val roomService: RoomService
) {
    @GetMapping("/{id}")
    suspend fun getRoomApi(
            @PathVariable id: String
    ): String = handleApi (
            validator = { roomValidator.validateGetRoom(id) },
            supplier = { roomService.getRoomService(id) }
    )
}