package via.easyflow.interactor.controller.task.model.attribute

data class BaseTasksRequestsAttribute(
    val userId: String,
    val projectId: String,
    val taskId: String?
) {

}