package nexio.api_server.application.service

import org.springframework.stereotype.Component

@Component
class RoomService {
    suspend fun getRoomsService(): String {
        return "body"
    }
    suspend fun getRoomService(id: String): String {
        return "body"
    }
}