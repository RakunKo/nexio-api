package nexio.api_server.domain.enums

enum class Roles (val value: String) {
    USER("USER"),
    ADMIN("ADMIN");

    companion object {
        fun from(value: String): Roles = Roles.entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Unknown status: $value")
    }
}