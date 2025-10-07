package nexio.api_server.application.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import nexio.api_server.application.validator.annotations.NotSpecial
import nexio.api_server.domain.User
import nexio.api_server.domain.enums.Roles
import nexio.api_server.domain.enums.UserStatus

data class SignupRequest(
        @field:NotBlank(message = "Name must not be blank")
        @field:Size(max = 20, message = "Name must be at most 20 characters")
        @field:NotSpecial(message = "Name must not contain special characters")
        val name: String,

        @field:NotBlank(message = "Email must not be blank")
        @field:Size(max = 50, message = "Email must be at most 50 characters")
        @field:Email(message = "Invalid email format")
        val email: String,

        @field:NotBlank(message = "Provider Id must not be blank")
        @field:Size(max = 100, message = "Provider Id must be at most 100 characters")
        val providerId: String,

        @field:Size(max = 255, message = "Profile image must be at most 255 characters")
        val profileImg: String?
) {
    fun toEntity(): User = User(
            name = name,
            email = email,
            providerId = providerId,
            profileImg = profileImg,
            role = Roles.USER,
            status = UserStatus.ACTIVE
    )
}
