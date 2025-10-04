package nexio.api_server.application.handler

suspend fun <R> handleApi(
        validator: suspend () -> Unit = {},
        supplier: suspend () -> R
): R {
    validator()
    return supplier()
}