package nexio.api_server.application.dto.room

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import nexio.api_server.domain.Room
import java.util.*

data class PostRoomRequest(
        @field:NotBlank(message = "Room name must not be blank")
        @field:Size(max = 20, message = "Room name must be at most 200 characters")
        val name: String,

        @field:Size(max = 100, message = "Description must be at most 100 characters")
        val description: String? = null,

        @field:NotNull(message = "User ID must not be null")
        val userId: UUID
) {
        fun toEntity(): Room = Room(
                name = name,
                description = description,
                userId = userId
        )
}