package nexio.api_server.domain

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("user")
data class User(
        @Column("name")
        val name: String,
): BaseEntity()
