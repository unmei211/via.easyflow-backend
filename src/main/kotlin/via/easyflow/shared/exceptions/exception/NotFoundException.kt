package via.easyflow.shared.exceptions.exception



class NotFoundException(
    message: String,
    cause: Throwable? = null,
    val errors: List<String> = emptyList(),
) : RuntimeException(message, cause)
