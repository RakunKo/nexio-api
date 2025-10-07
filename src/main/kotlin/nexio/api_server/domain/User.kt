package nexio.api_server.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import nexio.api_server.application.validator.annotations.NotSpecial
import nexio.api_server.domain.enums.Roles
import nexio.api_server.domain.enums.UserStatus
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("user")
class User(
        @Column("name")
        val name: String,

        @Column("email")
        val email: String,

        @Column("provider_id")
        val providerId: String,

        @Column("profile_img")
        val profileImg: String?,

        @Column("role")
        @get:JsonIgnore
        val role: Roles = Roles.USER,

        @Column("status")
        val status: UserStatus = UserStatus.ACTIVE,
): BaseEntity()
