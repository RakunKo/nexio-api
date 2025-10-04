package nexio.api_server.application.validator

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class RoomValidator {
    suspend fun validateGetRooms() {
        //id 검증 로직
    }

    suspend fun validateGetRoom(id: String) {
        //id 검증 로직
    }
}