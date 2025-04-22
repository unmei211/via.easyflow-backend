package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.GetTasksByProjectIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel

@Component
class GetTasksByProjectCase(
    private val taskService: ITaskService,
) : UseCase<GetTasksByProjectCase.Input, Flux<TaskModel>> {
    override fun invoke(input: Input): Flux<TaskModel> {
        val tasks = taskService.getTasksByProject(
            GetTasksByProjectIn(
                projectId = input.projectId
            )
        )
        return tasks
    }

    data class Input(val projectId: String)
}