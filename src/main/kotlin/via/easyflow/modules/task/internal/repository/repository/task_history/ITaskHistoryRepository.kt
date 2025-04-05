package via.easyflow.modules.task.internal.repository.repository.task_history

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.repository.model.TaskHistoryEntity

interface ITaskHistoryRepository {
    fun add(taskHistory: TaskHistoryEntity): Mono<TaskHistoryEntity>;
    fun update(taskHistory: TaskHistoryEntity): Mono<TaskHistoryEntity>;
    fun deleteById(subtaskId: String): Mono<String>;
    fun existsById(subtaskId: String): Mono<Boolean>;
}