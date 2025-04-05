package via.easyflow.modules.task.api.model.base

import java.time.Instant

data class SubtaskModel(
    val subtaskId: String? = null,
    val taskId: String,
    val title: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

}