package nexio.api_server.application.common.code

import org.springframework.http.HttpStatus

interface BaseErrorCode {
    val httpStatus: HttpStatus
    val message: String
}