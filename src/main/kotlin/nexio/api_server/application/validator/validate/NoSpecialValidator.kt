package nexio.api_server.application.validator.validate

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import nexio.api_server.application.common.utils.StringValidator.containsSpecialCharacter
import nexio.api_server.application.validator.annotations.NotSpecial

class NoSpecialValidator: ConstraintValidator<NotSpecial, String> {
    override fun isValid(p0: String?, p1: ConstraintValidatorContext?): Boolean {
        if (p0 == null) return true
        return !p0.containsSpecialCharacter()
    }
}