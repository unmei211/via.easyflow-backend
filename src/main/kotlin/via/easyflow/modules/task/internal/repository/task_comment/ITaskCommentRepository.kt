package via.easyflow.modules.task.internal.repository.task_comment

import reactor.core.publisher.Mono
import via.easyflow.modules.task.internal.entity.TaskCommentEntity

interface ITaskCommentRepository {
    fun add(taskComment: TaskCommentEntity): Mono<TaskCommentEntity>;
    fun update(taskComment: TaskCommentEntity): Mono<TaskCommentEntity>;
    fun deleteById(subtaskId: String): Mono<String>;
    fun existsById(subtaskId: String): Mono<Boolean>;
}