package via.easyflow.modules.task.internal.repository.task

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.entity.TaskEntity

class TaskRepository : ITaskRepository {
    override fun add(task: TaskEntity): Mono<TaskEntity> {
        TODO("Not yet implemented")
    }

    override fun update(task: TaskEntity): Mono<TaskEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(taskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(taskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}