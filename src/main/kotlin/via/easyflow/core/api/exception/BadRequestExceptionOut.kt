package via.easyflow.core.api.exception

data class BadRequestExceptionOut(
    val errors: List<String>,
    val message: String,
) {

}