package via.easyflow.interactor.interactors.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.core.exception.ForbiddenException
import via.easyflow.core.exception.NotFoundException
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.interactor.usecases.project.ProjectIsAvailableForUserCase
import via.easyflow.interactor.usecases.project.ProjectIsAvailableForUserCaseInput
import via.easyflow.interactor.usecases.project.ProjectMustBeAvailableForUserCase
import via.easyflow.interactor.usecases.task.*
import via.easyflow.interactor.usecases.user.UserIsAvailableCase
import via.easyflow.interactor.usecases.user.UserIsAvailableUseCaseInput
import via.easyflow.interactor.usecases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.user.UserMustBeAvailableUseCaseInput
import via.easyflow.modules.task.api.model.base.TaskModel

@Component
class TaskInteractor(
    private val projectIsAvailableForUserCase: ProjectIsAvailableForUserCase,
    private val userIsAvailableCase: UserIsAvailableCase,
    private val addTaskCase: AddTaskCase,
    private val getUserTasksInProjectCase: GetUserTasksInProjectCase,
    private val userMustBeAvailableCase: UserMustBeAvailableCase,
    private val projectMustBeAvailableForUserCase: ProjectMustBeAvailableForUserCase,
    private val changeTaskCase: ChangeTaskCase
) : ITaskInteractor {
    override fun changeTask(input: ChangeTaskInteractorInput): Mono<TaskModel> {
        val projectValidated = projectMustBeAvailableForUserCase.invoke(
            ProjectIsAvailableForUserCaseInput(
                userId = input.changerUserId,
                projectId = input.projectId,
            )
        )
        val userValidated = userMustBeAvailableCase.invoke(
            input = UserMustBeAvailableUseCaseInput(
                userId = input.changerUserId
            )
        )

        return projectValidated.zipWith(userValidated)
            .flatMap {
                changeTaskCase.invoke(
                    ChangeTaskCaseInput(
                        taskName = input.name,
                        taskDescription = input.description,
                        userId = input.changerUserId,
                        projectId = input.projectId,
                        taskVersion = input.version,
                        taskStatus = input.status,
                        taskId = input.taskId,
                    )
                )
            }
    }

    override fun getUserTasksInProject(input: AddUserTasksInProjectInteractorInput): Flux<TaskModel> {
        val projectIsAvailableValidated = projectIsAvailableForUserCase.invoke(
            ProjectIsAvailableForUserCaseInput(
                userId = input.userId,
                projectId = input.projectId,
            )
        ).flatMap {
            if (it) just(it)
            else error(ForbiddenException("User tasks not available"))
        }

        val userIsAvailableValidated = userMustBeAvailableCase.invoke(
            UserMustBeAvailableUseCaseInput(
                userId = input.userId
            )
        )

        return projectIsAvailableValidated.zipWith(userIsAvailableValidated)
            .thenMany(
                getUserTasksInProjectCase.invoke(
                    GetUserTasksInProjectCaseInput(
                        projectId = input.projectId,
                        userId = input.userId
                    )
                )
            )
    }

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