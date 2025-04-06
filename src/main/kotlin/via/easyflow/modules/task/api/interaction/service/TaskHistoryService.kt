package via.easyflow.modules.task.api.interaction.service

import org.springframework.stereotype.Component
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.task.api.contract.`in`.WriteTaskHistoryIn
import via.easyflow.modules.task.internal.repository.model.TaskHistoryEntity
import via.easyflow.modules.task.internal.repository.repository.task_history.ITaskHistoryRepository
import via.easyflow.modules.task.internal.repository.repository.task_history.TaskHistoryRepository

@Component
class TaskHistoryService(
    private val taskHistoryRepository: ITaskHistoryRepository
) : ITaskHistoryService {
    override fun writeTaskHistory(taskIn: WriteTaskHistoryIn) {
        val task = taskIn.task
        taskHistoryRepository.add(TaskHistoryEntity(
            taskHistoryId = uuid(),
            taskId = task.taskId,
            name = TODO(),
            description = TODO(),
            status = TODO(),
            createdAt = TODO(),
            updatedAt = TODO(),
            ownerUserId = TODO(),
            projectId = TODO(),
            version = TODO(),
            moveAt = TODO()
        ))
    }
}