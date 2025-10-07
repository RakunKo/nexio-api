package nexio.api_server.application.common.status

import nexio.api_server.application.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorStatus(
        override val status: HttpStatus,
        override val message: String
): BaseErrorCode {
    NAME_IS_ALREADY_USED(HttpStatus.CONFLICT, "User name %s is already used"),
}