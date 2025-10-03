package nexio.api_server.common.status

import nexio.api_server.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class CommonErrorStatus(
        override val httpStatus: HttpStatus,
        override val message: String
): BaseErrorCode {
    PATH_PARAMS_INVALID(HttpStatus.BAD_REQUEST, "Invalid value %s for path parameters %s"),
    PATH_PARAMS_TYPE_INVALID(HttpStatus.BAD_REQUEST, "Invalid value type for path parameters %s")
}