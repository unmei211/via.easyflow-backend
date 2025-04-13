package via.easyflow.modules.task.service

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.shared.modules.task.api.inputs.WriteTaskHistoryIn
import via.easyflow.shared.modules.task.api.service.ITaskHistoryService
import via.easyflow.modules.task.repository.repository.task_history.ITaskHistoryRepository
import java.time.Instant

@Component
class TaskHistoryService(
    private val taskHistoryRepository: ITaskHistoryRepository
) : ITaskHistoryService {
    override fun writeTaskHistory(taskIn: WriteTaskHistoryIn): Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity> {
        val task = taskIn.task
        val taskHistoryEntity: Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity> = taskHistoryRepository.add(
            via.easyflow.modules.task.repository.model.TaskHistoryEntity(
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