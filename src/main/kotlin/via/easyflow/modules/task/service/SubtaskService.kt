package via.easyflow.modules.task.service

import java.time.Instant
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.task.repository.model.SubtaskEntity
import via.easyflow.modules.task.repository.repository.subtask.ISubtaskRepository
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.inputs.subtask.AddSubtaskIn
import via.easyflow.shared.modules.task.api.inputs.subtask.ChangeSubtaskIn
import via.easyflow.shared.modules.task.api.inputs.subtask.GetSubtasksByTaskIdInput
import via.easyflow.shared.modules.task.api.service.ISubtaskService
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.SubtaskModel

@Service
class SubtaskService(
    private val taskService: ITaskService,
    private val subtaskRepository: ISubtaskRepository,
) : ISubtaskService {
    override fun addSubtask(input: AddSubtaskIn): Mono<SubtaskModel> {
        val task = taskService.getTaskById(
            GetTaskByIdIn(
                taskId = input.taskId
            )
        )

        val subtask: Mono<SubtaskModel> = task.flatMap {
            subtaskRepository.add(
                SubtaskEntity(
                    subtaskId = uuid(),
                    taskId = it.taskId!!,
                    title = input.title,
                    ownerId = input.ownerId,
                    assigneeId = input.assigneeId,
                    status = "TODO",
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    description = input.description,
                )
            )
        }.map { it.toModel() }

        return subtask
    }

    override fun changeSubtask(input: ChangeSubtaskIn): Mono<SubtaskModel> {
        TODO("Not yet implemented")
    }

    override fun getSubtasksByTaskId(input: GetSubtasksByTaskIdInput): Flux<SubtaskModel> {
        TODO("Not yet implemented")
    }
}