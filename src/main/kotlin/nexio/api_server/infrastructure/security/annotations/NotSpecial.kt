package nexio.api_server.application.validator.annotations

import jakarta.validation.Constraint
import jakarta.validation.Payload
import nexio.api_server.application.validator.validate.NoSpecialValidator
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthUser
