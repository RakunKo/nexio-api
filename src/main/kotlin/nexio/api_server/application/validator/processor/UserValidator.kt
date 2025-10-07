package nexio.api_server.application.validator.processor

import nexio.api_server.application.common.exception.ApiException
import nexio.api_server.application.common.status.UserErrorStatus
import nexio.api_server.application.dto.user.SignupRequest
import nexio.api_server.infrastructure.adapter.persistence.UserRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.UUID

@Component
class UserValidator(
        private val userRepository: UserRepository
) {
    suspend fun validateSignUp(body: SignupRequest) {
        if(userRepository.existsUserByName(body.name)) throw ApiException(UserErrorStatus.NAME_IS_ALREADY_USED, listOf(body.name))
    }

}