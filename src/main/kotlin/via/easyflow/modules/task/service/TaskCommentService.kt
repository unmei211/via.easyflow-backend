package via.easyflow.modules.task.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import via.easyflow.modules.task.repository.model.TaskCommentEntity
import via.easyflow.modules.task.repository.repository.task_comment.ITaskCommentRepository
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.inputs.task_comment.PushTaskCommentIn
import via.easyflow.shared.modules.task.api.service.ITaskCommentService
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskCommentModel

@Service
class TaskCommentService(
    private val taskCommentRepository: ITaskCommentRepository,
    private val taskService: ITaskService,
) : ITaskCommentService {
    override fun pushTaskComment(input: PushTaskCommentIn): Mono<TaskCommentModel> {
        val taskMono = taskService.getTaskById(GetTaskByIdIn(input.taskId))

        val taskEntity: Mono<TaskCommentEntity> = taskMono.flatMap {
            taskCommentRepository.add(
                TaskCommentEntity.new(
                    userId = input.userId,
                    comment = input.comment,
                    taskId = input.taskId
                )
            )
        }

        return taskEntity.map { it.toModel() }
    }
}