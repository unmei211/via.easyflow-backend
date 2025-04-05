package via.easyflow.modules.task.api.model.base

import via.easyflow.modules.task.internal.repository.model.TaskEntity
import java.time.Instant

data class TaskModel(
    val name: String,
    val taskId: String? = null,
    val description: String? = null,
    val status: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val ownerUserId: String,
    val projectId: String,
    val version: String
) {
    fun toEntity(): TaskEntity {
        return TaskEntity(
            name = this.name,
            taskId = this.taskId!!,
            description = description,
            status = status,
            createdAt = createdAt!!,
            updatedAt = updatedAt,
            ownerUserId = ownerUserId,
            projectId = projectId,
            version = version
        )
    }

    companion object {
        fun from(entity: TaskEntity): TaskModel {
            return TaskModel(
                name = entity.name,
                taskId = entity.taskId,
                description = entity.description,
                status = entity.status,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                ownerUserId = entity.ownerUserId,
                projectId = entity.projectId,
                version = entity.version,
            )
        }
    }
}