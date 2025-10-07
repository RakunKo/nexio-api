package nexio.api_server.application.validator.processor

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.UUID

@Component
class RoomValidator {
    suspend fun validateGetRooms() {
        //id 검증 로직
    }

    suspend fun validateGetRoom(id: UUID) {
        //id 검증 로직
    }
}