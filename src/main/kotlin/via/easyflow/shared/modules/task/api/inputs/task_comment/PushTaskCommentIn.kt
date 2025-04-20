package via.easyflow.shared.modules.task.api.inputs.task_comment

data class PushTaskCommentIn(
    val taskId: String,
    val userId: String,
    val comment: String
) {
}