package nexio.api_server.application.handler

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

suspend fun <T> handleRoute(
        request: ServerRequest,
        validator: suspend (ServerRequest) -> T,
        supplier: suspend (T) -> Any
): ServerResponse {
    val validatedData = validator(request)
    val result = supplier(validatedData)
    return ServerResponse.ok().bodyValueAndAwait(result)
}

suspend fun handleRoute(
        request: ServerRequest,
        supplier: suspend () -> Any
): ServerResponse {
    val result = supplier()
    return ServerResponse.ok().bodyValueAndAwait(result)
}