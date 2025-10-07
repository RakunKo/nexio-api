package nexio.api_server.application.common.status

import nexio.api_server.application.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class CommonErrorStatus(
        override val status: HttpStatus,
        override val message: String
): BaseErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource with id %s does not exist"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Authentication is required or has failed"),
    FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "Role is insufficient permissions"),
    PATH_PARAMS_INVALID(HttpStatus.BAD_REQUEST, "Invalid value %s for path parameters %s"),
    PATH_PARAMS_TYPE_INVALID(HttpStatus.BAD_REQUEST, "Invalid value type for path parameters %s")
}