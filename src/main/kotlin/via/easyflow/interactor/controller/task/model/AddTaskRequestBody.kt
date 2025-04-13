package via.easyflow.interactor.controller.task.model

data class AddTaskRequestBody(
    val taskName: String,
    val taskDescription: String,
)