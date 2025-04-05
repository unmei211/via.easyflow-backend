package via.easyflow.modules.task.internal.repository.repository.task_history

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.repository.model.TaskHistoryEntity

class TaskHistoryRepository : ITaskHistoryRepository {
    override fun add(taskHistory: TaskHistoryEntity): Mono<TaskHistoryEntity> {
        TODO("Not yet implemented")
    }

    override fun update(taskHistory: TaskHistoryEntity): Mono<TaskHistoryEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(subtaskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(subtaskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}