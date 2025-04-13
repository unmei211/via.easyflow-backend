package via.easyflow.shared.exceptions.view

data class NotFoundExceptionOut(
    val errors: List<String>,
    val message: String,
) {
}