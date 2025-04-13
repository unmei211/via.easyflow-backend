package via.easyflow.shared.exceptions.view

data class ConflictExceptionOut(
    val errors: List<String>,
    val message: String,
) {

}