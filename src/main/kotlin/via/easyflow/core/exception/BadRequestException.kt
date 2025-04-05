package via.easyflow.core.exception

import org.springframework.modulith.NamedInterface

@NamedInterface
class BadRequestException(
    message: String,
    cause: Throwable? = null,
    val errors: List<String> = emptyList(),
) : RuntimeException(message, cause)
