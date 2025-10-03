package nexio.api_server.application.utils

import nexio.api_server.common.exception.BadRequestException
import nexio.api_server.common.status.CommonErrorStatus
import org.springframework.web.reactive.function.server.ServerRequest

fun ServerRequest.pathInt(key: String) : Int {
    return pathString(key)?.toIntOrNull()
            ?: throw BadRequestException(CommonErrorStatus.PATH_PARAMS_TYPE_INVALID, listOf(key))
}

fun ServerRequest.pathString(key: String): String {
    return pathVariable(key).takeIf { it.isNotBlank() }
            ?: throw BadRequestException(CommonErrorStatus.PATH_PARAMS_INVALID, listOf(key, null))
}