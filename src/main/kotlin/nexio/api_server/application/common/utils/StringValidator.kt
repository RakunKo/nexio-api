package nexio.api_server.application.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object StringValidator {
    private val SPECIAL_CHAR_REGEX = Regex("[^A-Za-z0-9가-힣]")

    fun String.containsSpecialCharacter(): Boolean = SPECIAL_CHAR_REGEX.containsMatchIn(this)
}