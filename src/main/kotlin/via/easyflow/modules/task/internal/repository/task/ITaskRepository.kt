package via.easyflow.modules.task.internal.repository.task

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.entity.TaskEntity

interface ITaskRepository {
    fun add(task: TaskEntity): Mono<TaskEntity>;
    fun update(task: TaskEntity): Mono<TaskEntity>;
    fun deleteById(taskId: String): Mono<String>;
    fun existsById(taskId: String): Mono<Boolean>;
}