package nexio.api_server.infrastructure.security.jwt

import io.jsonwebtoken.Jwts
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import nexio.api_server.domain.User
import nexio.api_server.infrastructure.security.key.JwtKey
import nexio.api_server.infrastructure.security.key.KeyManager
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.security.Key
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtProvider(
        private val keyManager: KeyManager,
) {
    private var cachedJwtKey: JwtKey? = null
    private var keyLoadedAt: Instant? = null
    private val cacheExpiry = Duration.ofHours(1)
    suspend fun generateJwtToken(subject: String, user: User): JwtToken = JwtToken(
            accessToken = generateToken(subject, user, 3600),
            refreshToken = generateToken(subject, user, 604800)
    )

    fun getJwtDecoder(): ReactiveJwtDecoder {
        val publicKey = runBlocking { getKey().publicKey.toPublicKey() }
        return NimbusReactiveJwtDecoder.withPublicKey(publicKey as RSAPublicKey).build()
    }
    fun generateJwtTokenMono(subject: String, user: User): Mono<JwtToken> = mono { generateJwtToken(subject, user) }

    private suspend fun generateToken(subject: String, user: User, expiration: Long): String {
        val now = Instant.now()

        return Jwts.builder()
                .subject(subject)
                .apply {
                    claim("roles", user.role)
                    claim("id", user.id)
                    claim("status", user.status)
                }
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expiration)))
                .signWith(getKey().privateKey.toPrivateKey())
                .compact()
    }

    private suspend fun getKey(): JwtKey {
        val now = Instant.now()
        if (cachedJwtKey == null || keyLoadedAt == null ||
                Duration.between(keyLoadedAt, now) > cacheExpiry) {
            cachedJwtKey = keyManager.getActiveKeys().first()
            keyLoadedAt = now
        }
        return cachedJwtKey!!
    }

    private fun ByteArray.toPrivateKey(): Key {
        val keySpec = PKCS8EncodedKeySpec(this)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }

    private fun ByteArray.toPublicKey(): Key {
        val keySpec = X509EncodedKeySpec(this)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }
}