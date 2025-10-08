package nexio.api_server.infrastructure.adapter.api

import jakarta.validation.Valid
import nexio.api_server.application.dto.user.SignupRequest
import nexio.api_server.application.handler.handleApi
import nexio.api_server.application.service.UserService
import nexio.api_server.application.validator.annotations.AuthUser
import nexio.api_server.application.validator.processor.UserValidator
import nexio.api_server.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserApi (
        private val userValidator: UserValidator,
        private val userService: UserService
) {
    @PostMapping("/signup")
    suspend fun signUpApi(
            @Valid @RequestBody body: SignupRequest
    ): ResponseEntity<User> = handleApi (
        status = HttpStatus.CREATED,
        validator = { userValidator.validateSignUp(body) },
        supplier = { userService.signup(body) }
    )

    @GetMapping("/profile")
    suspend fun getMyProfile(@AuthUser user: User): ResponseEntity<User> = handleApi(
            status = HttpStatus.OK,
            validator = {  },
            supplier = { user }
    )

}