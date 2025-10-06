package nexio.api_server.application.service

import nexio.api_server.application.common.exception.ApiException
import nexio.api_server.application.common.status.CommonErrorStatus
import nexio.api_server.application.dto.room.PostRoomRequest
import nexio.api_server.domain.Room
import nexio.api_server.infrastructure.adapter.persistence.RoomRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RoomService(
        private val roomRepository: RoomRepository
) {
    suspend fun getRooms(): String {
        return "body"
    }
    suspend fun getRoom(id: UUID): Room {
        return roomRepository.findRoomById(id)
                ?: throw ApiException(CommonErrorStatus.NOT_FOUND, listOf(id))
    }

    suspend fun postRoom(body: PostRoomRequest): Room {
        val entity = body.toEntity()
        println("Entity before save: id=${entity.id}, roomId=${entity.getId()}")

        val saved = roomRepository.save(entity)
        println("Entity after save: id=${saved.id}, roomId=${saved.getId()}")

        return saved
    }
}