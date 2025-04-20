package via.easyflow.shared.modules.task.model

import java.time.Instant

data class SubtaskModel(
    val subtaskId: String? = null,
    val taskId: String,
    val title: String,
    val ownerId: String,
    val description: String? = null,
    val assigneeId: String? = null,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

}