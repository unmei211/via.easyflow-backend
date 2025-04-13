package via.easyflow.modules.task.repository.repository.task_history

import reactor.core.publisher.Mono
import via.easyflow.modules.task.repository.model.TaskHistoryEntity

interface ITaskHistoryRepository {
    fun add(taskHistory: via.easyflow.modules.task.repository.model.TaskHistoryEntity): Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity>;
    fun update(taskHistory: via.easyflow.modules.task.repository.model.TaskHistoryEntity): Mono<via.easyflow.modules.task.repository.model.TaskHistoryEntity>;
    fun deleteById(subtaskId: String): Mono<String>;
    fun existsById(subtaskId: String): Mono<Boolean>;
}