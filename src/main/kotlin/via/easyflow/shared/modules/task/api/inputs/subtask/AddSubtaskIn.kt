package via.easyflow.shared.modules.task.api.inputs.subtask

data class AddSubtaskIn(
    val ownerId: String,
    val taskId: String,
    val assigneeId: String? = null,
    val description: String? = null,
    val title: String
)