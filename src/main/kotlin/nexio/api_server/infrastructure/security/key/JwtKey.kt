package nexio.api_server.infrastructure.security.key

import nexio.api_server.domain.BaseEntity
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("jwt_keys")
class JwtKey(
        @Column("private_key")
        val privateKey: ByteArray,

        @Column("public_key")
        val publicKey: ByteArray,

        @Column("active")
        val active: Boolean = true,
): BaseEntity()
