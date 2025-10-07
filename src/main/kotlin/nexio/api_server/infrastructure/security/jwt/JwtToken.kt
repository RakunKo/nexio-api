package nexio.api_server.infrastructure.security.jwt

data class JwtToken(
        val accessToken: String,
        val refreshToken: String
)