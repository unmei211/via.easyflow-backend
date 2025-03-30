package via.easyflow.modules.task.internal.entity

import java.time.Instant

data class SubtaskEntity(
    val subtaskId: String? = null,
    val taskId: String,
    val title: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

}