package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel

@Component
class GetTaskByIdCase(
    private val taskService: ITaskService,
) : UseCase<GetTaskByIdCase.Input, Mono<TaskModel>> {
    override fun invoke(input: GetTaskByIdCase.Input): Mono<TaskModel> {
        return taskService.getTaskById(GetTaskByIdIn(input.taskId))
    }

    data class Input(val taskId: String)
}