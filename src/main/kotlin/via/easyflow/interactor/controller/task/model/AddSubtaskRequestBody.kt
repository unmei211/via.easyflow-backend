package via.easyflow.interactor.controller.task.model

data class AddSubtaskRequestBody(
    val assigneeId: String? = null,
    val description: String? = null,
    val title: String
) {
}