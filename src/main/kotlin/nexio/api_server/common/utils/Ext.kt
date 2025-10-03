package nexio.api_server.common.utils

fun String.formatErrorMessage(data: List<Any?>): String =
        if (data.isEmpty()) this
        else this.format(*data.toTypedArray())