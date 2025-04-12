package via.easyflow.core.api.exception

data class ConflictExceptionOut(
    val errors: List<String>,
    val message: String,
) {

}