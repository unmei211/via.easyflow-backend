package via.easyflow.modules.task.internal.service

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.task.api.contract.`in`.WriteTaskHistoryIn
import via.easyflow.modules.task.api.interaction.service.ITaskHistoryService
import via.easyflow.modules.task.internal.repository.model.TaskHistoryEntity
import via.easyflow.modules.task.internal.repository.repository.task_history.ITaskHistoryRepository
import java.time.Instant

@Component
class TaskHistoryService(
    private val taskHistoryRepository: ITaskHistoryRepository
) : ITaskHistoryService {
    override fun writeTaskHistory(taskIn: WriteTaskHistoryIn): Mono<TaskHistoryEntity> {
        val task = taskIn.task
        val taskHistoryEntity: Mono<TaskHistoryEntity> = taskHistoryRepository.add(
            TaskHistoryEntity(
                taskHistoryId = uuid(),
                taskId = task.taskId!!,
                name = task.name,
                description = task.description,
                status = task.status,
                createdAt = task.createdAt!!,
                updatedAt = task.updatedAt ?: Instant.now(),
                ownerUserId = task.ownerUserId,
                projectId = task.projectId,
                version = task.version,
                moveAt = Instant.now(),
                changerId = taskIn.changerId,
            )
        )
        return taskHistoryEntity
    }
}