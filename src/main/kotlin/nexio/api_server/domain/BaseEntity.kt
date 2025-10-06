package nexio.api_server.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import java.time.Instant
import java.util.*

abstract class BaseEntity (
        @Id
        @Column("id")
        open var entityId: UUID = UUID.randomUUID(),

        @CreatedDate
        @Column("created_at")
        var createdAt: Instant? = null,

        @LastModifiedDate
        @Column("updated_at")
        var updatedAt: Instant? = null
) : Persistable<UUID> {
        override fun getId(): UUID? = entityId
        @JsonIgnore
        override fun isNew(): Boolean = createdAt==null
}