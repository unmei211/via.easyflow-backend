package via.easyflow.shared.exceptions.view

data class BadRequestExceptionOut(
    val errors: List<String>,
    val message: String,
) {

}