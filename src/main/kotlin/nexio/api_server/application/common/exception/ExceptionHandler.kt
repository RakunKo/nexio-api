package nexio.api_server.application.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: WebExchangeBindException): Mono<ResponseEntity<Map<String, Any>>> {
        val messages = ex.bindingResult.fieldErrors.joinToString(", ") { err ->
            "${err.field}: ${err.defaultMessage ?: "Invalid value"}"
        }

        val response = mapOf(
                "status" to HttpStatus.BAD_REQUEST.value(),
                "message" to messages
        )

        return Mono.just(ResponseEntity.badRequest().body(response))
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): Mono<ResponseEntity<Map<String, Any>>> = Mono.just(ResponseEntity.status(ex.status).body(ex.toResponse()))




}