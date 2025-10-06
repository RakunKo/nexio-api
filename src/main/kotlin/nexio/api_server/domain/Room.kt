package nexio.api_server.domain

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("room")
class Room (
        @Column("name")
        val name: String,

        @Column("description")
        val description: String?,

        @Column("user_id")
        val userId: UUID
): BaseEntity()
