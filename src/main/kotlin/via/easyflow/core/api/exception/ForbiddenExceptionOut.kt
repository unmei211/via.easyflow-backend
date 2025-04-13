package via.easyflow.core.api.exception

data class ForbiddenExceptionOut(
    val errors: List<String>,
    val message: String,
) {
}