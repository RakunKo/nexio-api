package nexio.api_server.infrastructure.adapter.persistence

import nexio.api_server.domain.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CoroutineCrudRepository<User, UUID> {
    suspend fun existsUserByName(name: String): Boolean
    suspend fun findUserById(id: UUID): User?
    suspend fun findUserByProviderId(providerId: String): User?

}