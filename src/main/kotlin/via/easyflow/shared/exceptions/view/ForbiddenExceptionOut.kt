package via.easyflow.shared.exceptions.view

data class ForbiddenExceptionOut(
    val errors: List<String>,
    val message: String,
) {
}