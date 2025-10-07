package nexio.api_server.infrastructure.adapter.persistence

import nexio.api_server.infrastructure.security.key.JwtKey
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface KeyRepository : CoroutineCrudRepository<JwtKey, UUID> {
    suspend fun findJwtKeysByActiveIsTrue(): List<JwtKey>
    suspend fun deleteAllByActiveIsFalseAndCreatedAtBefore(cutoff: Instant)
}