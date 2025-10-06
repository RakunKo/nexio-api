package nexio.api_server.infrastructure.adapter.persistence

import nexio.api_server.domain.Room
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoomRepository : CoroutineCrudRepository<Room, UUID> {
    suspend fun findRoomById(id: UUID): Room?
}