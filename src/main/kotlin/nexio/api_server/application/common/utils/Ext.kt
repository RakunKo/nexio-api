package nexio.api_server.application.common.utils

import com.fasterxml.jackson.databind.ser.Serializers.Base
import nexio.api_server.domain.BaseEntity
import java.nio.ByteBuffer
import java.util.UUID

fun String.formatErrorMessage(data: List<Any?>): String =
        if (data.isEmpty()) this
        else this.format(*data.toTypedArray())
