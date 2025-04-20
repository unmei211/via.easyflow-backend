package via.easyflow.shared.exceptions.exception

class BadRequestException(
    message: String,
    cause: Throwable? = null,
    val errors: List<String> = emptyList(),
) : RuntimeException(message, cause)
