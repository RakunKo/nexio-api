package nexio.api_server.application.common.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(WebExchangeBindException::class)
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

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): Mono<ResponseEntity<Map<String, Any>>> {
        val messages = ex.constraintViolations.joinToString(", ") { violation ->
            "${violation.propertyPath}: ${violation.message}"
        }
        val response = mapOf(
                "status" to HttpStatus.BAD_REQUEST.value(),
                "message" to messages
        )
        return Mono.just(ResponseEntity.badRequest().body(response))
    }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleWebInputException(ex: ServerWebInputException): Mono<ResponseEntity<Map<String, Any>>> {
        val response = mapOf(
                "status" to HttpStatus.BAD_REQUEST.value(),
                "message" to "Invalid request value: ${ex.reason}"
        )
        return Mono.just(ResponseEntity.badRequest().body(response))
    }
}