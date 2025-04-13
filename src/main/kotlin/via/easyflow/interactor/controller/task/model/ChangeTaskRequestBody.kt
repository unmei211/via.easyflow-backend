package via.easyflow.interactor.controller.task.model

data class ChangeTaskRequestBody(
    val version: String,
    val description: String,
    val status: String,
    val name: String,
    val userId: String
)