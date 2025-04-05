package via.easyflow.modules.task.internal.repository.repository.task

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.repository.model.TaskEntity

interface ITaskRepository {
    fun add(task: TaskEntity): Mono<TaskEntity>;
    fun update(task: TaskEntity): Mono<TaskEntity>;
    fun deleteById(taskId: String): Mono<String>;
    fun existsById(taskId: String): Mono<Boolean>;

    fun getById(monoTaskId: Mono<String>): Mono<TaskEntity>;
}