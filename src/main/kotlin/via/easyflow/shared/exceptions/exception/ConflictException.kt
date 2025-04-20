package via.easyflow.shared.exceptions.exception


class ConflictException(
    message: String,
    cause: Throwable? = null,
    val errors: List<String> = emptyList(),
) : RuntimeException(message, cause)
