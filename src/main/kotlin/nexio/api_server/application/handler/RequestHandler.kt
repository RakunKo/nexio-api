package nexio.api_server.application.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

suspend fun <R> handleApi(
        status: HttpStatus,
        validator: suspend () -> Unit = {},
        supplier: suspend () -> R
): ResponseEntity<R> {
    validator()
    return ResponseEntity.status(status).body(supplier())
}