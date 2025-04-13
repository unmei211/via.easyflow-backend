package via.easyflow.modules.task.service.user_task.model

data class UserTaskFilterModel(
    val projectId: String? = null,
    val taskId: String? = null,
    val userId: String? = null,
    val ownerUserId: String? = null,

    ) {
}