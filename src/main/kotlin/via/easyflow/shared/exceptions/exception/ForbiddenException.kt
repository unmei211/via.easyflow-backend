package via.easyflow.shared.exceptions.exception

import org.springframework.modulith.NamedInterface

@NamedInterface
class ForbiddenException(
    message: String,
    cause: Throwable? = null,
    val errors: List<String> = emptyList(),
) : RuntimeException(message, cause)
