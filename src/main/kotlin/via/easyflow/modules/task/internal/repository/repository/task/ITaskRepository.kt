package via.easyflow.modules.task.internal.repository.repository.task

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.modules.task.internal.repository.contract.UpdateTaskMutation
import via.easyflow.modules.task.internal.repository.model.TaskEntity

interface ITaskRepository {
    fun add(task: TaskEntity): Mono<TaskEntity>;
    fun update(mutation: UpdateTaskMutation): Mono<TaskEntity>;
    fun deleteById(taskId: String): Mono<String>;
    fun existsById(taskId: String): Mono<Boolean>;

    fun getById(monoTaskId: Mono<String>): Mono<TaskEntity>;

    fun searchByFilter(filters: List<IQueryFilter>): Flux<TaskEntity>;
}