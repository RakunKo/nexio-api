package nexio.api_server.domain.enums

enum class UserStatus(val value: String) {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    SUSPENDED("SUSPENDED"),
    DELETED("DELETED");

    companion object {
        fun from(value: String): UserStatus = entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Unknown status: $value")
    }
}