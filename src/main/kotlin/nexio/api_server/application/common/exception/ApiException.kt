package nexio.api_server.application.common.exception

import nexio.api_server.application.common.code.BaseErrorCode
import nexio.api_server.application.common.utils.formatErrorMessage

class ApiException(
        val err: BaseErrorCode,
        val data: List<Any?> = emptyList()
) :RuntimeException() {
    val status = err.status

    fun toResponse(): Map<String, Any> = mapOf(
            "status" to err.status,
            "message" to err.message.formatErrorMessage(data)
    )
}