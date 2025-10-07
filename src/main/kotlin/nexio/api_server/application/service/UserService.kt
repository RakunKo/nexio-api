package nexio.api_server.application.service

import nexio.api_server.application.common.exception.ApiException
import nexio.api_server.application.common.status.CommonErrorStatus
import nexio.api_server.application.dto.room.PostRoomRequest
import nexio.api_server.application.dto.user.SignupRequest
import nexio.api_server.domain.Room
import nexio.api_server.domain.User
import nexio.api_server.infrastructure.adapter.persistence.RoomRepository
import nexio.api_server.infrastructure.adapter.persistence.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserService(
        private val userRepository: UserRepository
) {
    suspend fun signup(body: SignupRequest): User = userRepository.save(body.toEntity())
    suspend fun getUser(identifier: UserIdentifier): User? = when(identifier) {
        is UserIdentifier.ById -> userRepository.findUserById(identifier.id)
        is UserIdentifier.ByProvider -> userRepository.findUserByProviderId(identifier.providerId)
    }
}

sealed interface UserIdentifier {
    data class ById(val id: UUID): UserIdentifier
    data class ByProvider(val providerId: String): UserIdentifier
}