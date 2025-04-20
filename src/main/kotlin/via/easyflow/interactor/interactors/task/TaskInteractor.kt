package via.easyflow.interactor.interactors.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.interactor.interactors.task.contract.AddTaskCommentInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTasksByProjectInteractorInput
import via.easyflow.interactor.usecases.project.ProjectIsAvailableForUserCase
import via.easyflow.interactor.usecases.project.ProjectIsAvailableForUserCaseInput
import via.easyflow.interactor.usecases.project.ProjectMustBeAvailableForUserCase
import via.easyflow.interactor.usecases.task.AddTaskCase
import via.easyflow.interactor.usecases.task.AddTaskCaseInput
import via.easyflow.interactor.usecases.task.AddTaskCommentCase
import via.easyflow.interactor.usecases.task.ChangeTaskCase
import via.easyflow.interactor.usecases.task.ChangeTaskCaseInput
import via.easyflow.interactor.usecases.task.GetTaskByIdCase
import via.easyflow.interactor.usecases.task.GetTasksByProjectCase
import via.easyflow.interactor.usecases.task.GetUserTasksInProjectCase
import via.easyflow.interactor.usecases.task.GetUserTasksInProjectCaseInput
import via.easyflow.interactor.usecases.user.UserIsAvailableCase
import via.easyflow.interactor.usecases.user.UserIsAvailableUseCaseInput
import via.easyflow.interactor.usecases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.user.UserMustBeAvailableUseCaseInput
import via.easyflow.shared.exceptions.exception.ForbiddenException
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.task.model.TaskCommentModel
import via.easyflow.shared.modules.task.model.TaskModel

@Component
class TaskInteractor(
    // Project
    private val projectIsAvailableForUserCase: ProjectIsAvailableForUserCase,
    private val projectMustBeAvailableForUserCase: ProjectMustBeAvailableForUserCase,
    // User
    private val userIsAvailableCase: UserIsAvailableCase,
    private val userMustBeAvailableCase: UserMustBeAvailableCase,
    // Task
    private val addTaskCase: AddTaskCase,
    private val getUserTasksInProjectCase: GetUserTasksInProjectCase,
    private val changeTaskCase: ChangeTaskCase,
    private val getTaskByIdCase: GetTaskByIdCase,
    private val addTaskCommentCase: AddTaskCommentCase,
    private val getTasksByProjectCase: GetTasksByProjectCase
) : ITaskInteractor {
    override fun getTasksByProject(input: GetTasksByProjectInteractorInput): Flux<TaskModel> {
        return getTasksByProjectCase.invoke(
            GetTasksByProjectCase.Input(
                projectId = input.projectId
            )
        )
    }

    override fun addTaskComment(input: AddTaskCommentInteractorInput): Mono<TaskCommentModel> {
        val userAvailability: Mono<Unit> = userMustBeAvailableCase.invoke(
            UserMustBeAvailableUseCaseInput(
                userId = input.userId
            ),
        )
        val foundedTask: Mono<TaskModel> = getTaskByIdCase.invoke(GetTaskByIdCase.Input(input.taskId))

        return userAvailability.zipWith(foundedTask)
            .flatMap {
                projectMustBeAvailableForUserCase.invoke(
                    ProjectIsAvailableForUserCaseInput(
                        userId = input.userId,
                        projectId = it.t2.projectId,
                    ),
                )
            }.flatMap {
                addTaskCommentCase.invoke(
                    AddTaskCommentCase.Input(
                        taskId = input.taskId,
                        userId = input.userId,
                        comment = input.comment,
                    )
                )
            }
    }

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