package nexio.api_server.infrastructure.adapter.api

import jakarta.validation.Valid
import nexio.api_server.application.dto.room.PostRoomRequest
import nexio.api_server.application.handler.handleApi
import nexio.api_server.application.service.RoomService
import nexio.api_server.application.validator.processor.RoomValidator
import nexio.api_server.domain.Room
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/rooms")
class RoomApi (
        private val roomValidator: RoomValidator,
        private val roomService: RoomService
) {
    @Validated
    @PostMapping
    suspend fun postRoomApi(
            @Valid @RequestBody body: PostRoomRequest
    ): ResponseEntity<Room> = handleApi (
        status = HttpStatus.OK,
        validator = {  },
        supplier = { roomService.postRoom(body) }
    )

    @GetMapping("/{id}")
    suspend fun getRoomApi(
            @PathVariable id: UUID
    ): ResponseEntity<Room> = handleApi (
            status = HttpStatus.OK,
            validator = { roomValidator.validateGetRoom(id) },
            supplier = { roomService.getRoom(id) }
    )
}