package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.GetTasksByProjectIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class GetTasksByProjectCase(
    private val taskService: ITaskService,
) : TypedUseCase<GetTasksByProjectCase.Input, Flux<TaskModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

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