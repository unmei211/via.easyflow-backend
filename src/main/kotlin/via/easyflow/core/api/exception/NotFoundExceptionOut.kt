package via.easyflow.core.api.exception

data class NotFoundExceptionOut(
    val errors: List<String>,
    val message: String,
) {
}