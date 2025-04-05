package via.easyflow.modules.task.internal.repository.repository.task_comment

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.repository.model.TaskCommentEntity

class TaskCommentRepository : ITaskCommentRepository {
    override fun add(taskComment: TaskCommentEntity): Mono<TaskCommentEntity> {
        TODO("Not yet implemented")
    }

    override fun update(taskComment: TaskCommentEntity): Mono<TaskCommentEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteById(subtaskId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun existsById(subtaskId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}