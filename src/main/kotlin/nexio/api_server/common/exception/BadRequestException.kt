package nexio.api_server.common.exception

import nexio.api_server.common.code.BaseErrorCode
import nexio.api_server.common.utils.formatErrorMessage

class BadRequestException(
        private val err: BaseErrorCode,
        private val data: List<Any?> = emptyList()
) :RuntimeException(err.message.formatErrorMessage(data)) {
}