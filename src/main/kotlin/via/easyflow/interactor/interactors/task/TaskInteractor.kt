package via.easyflow.interactor.interactors.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.core.exception.NotFoundException
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.usecases.project.ProjectIsAvailableForUserCase
import via.easyflow.interactor.usecases.project.ProjectIsAvailableForUserCaseInput
import via.easyflow.interactor.usecases.task.model.AddTaskCase
import via.easyflow.interactor.usecases.task.model.AddTaskCaseInput
import via.easyflow.interactor.usecases.user.UserIsAvailableCase
import via.easyflow.interactor.usecases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.user.UserIsAvailableUseCaseInput
import via.easyflow.modules.task.api.model.base.TaskModel

@Component
class TaskInteractor(
    private val projectIsAvailableForUserCase: ProjectIsAvailableForUserCase,
    private val userIsAvailableCase: UserIsAvailableCase,
    private val addTaskCase: AddTaskCase
) : ITaskInteractor {
    override fun addTask(input: AddTaskInteractorInput): Mono<TaskModel> {
        val userCaseInput = UserIsAvailableUseCaseInput(
            userId = input.userId
        )

        val projectInput = ProjectIsAvailableForUserCaseInput(
            userId = input.userId,
            projectId = input.projectId
        )

        val addTaskInput = AddTaskCaseInput(
            taskName = input.taskName,
            taskDescription = input.taskDescription,
            userId = input.userId,
            projectId = input.projectId,
        )

        val userAvailableMono = userIsAvailableCase.invoke(userCaseInput)
            .flatMap {
                if (it) {
                    just(it)
                } else {
                    error(NotFoundException("Can't find user ${input.userId}"))
                }
            }

        val projectAvailableMono = projectIsAvailableForUserCase.invoke(projectInput)
            .flatMap {
                if (it) just(it)
                else error(NotFoundException("Can't find project"))
            }

        val validated = userAvailableMono.zipWith(projectAvailableMono).then()

        val addedTask: Mono<TaskModel> = validated.then(Mono.defer { addTaskCase.invoke(addTaskInput) })

        return addedTask
    }
}