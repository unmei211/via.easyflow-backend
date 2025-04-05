package via.easyflow.modules.task.internal.repository.model

import via.easyflow.modules.task.api.model.base.TaskModel
import java.time.Instant

data class TaskEntity(
    val name: String,
    val taskId: String,
    val description: String? = null,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val ownerUserId: String,
    val projectId: String
) {

}