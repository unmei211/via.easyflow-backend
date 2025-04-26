package via.easyflow.interactor.interactors.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import via.easyflow.core.tools.logger.logger
import via.easyflow.interactor.interactors.task.contract.AddSubtaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddTaskCommentInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTaskInfoInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTasksByProjectInteractorInput
import via.easyflow.interactor.usecases.cases.project.ProjectIsAvailableForUserCase
import via.easyflow.interactor.usecases.cases.project.ProjectMustBeAvailableForUserCase
import via.easyflow.interactor.usecases.cases.task.AddSubtaskCase
import via.easyflow.interactor.usecases.cases.task.AddTaskCase
import via.easyflow.interactor.usecases.cases.task.AddTaskCommentCase
import via.easyflow.interactor.usecases.cases.task.ChangeTaskCase
import via.easyflow.interactor.usecases.cases.task.GetSubtaskCase
import via.easyflow.interactor.usecases.cases.task.GetTaskByIdCase
import via.easyflow.interactor.usecases.cases.task.GetTaskCommentsCase
import via.easyflow.interactor.usecases.cases.task.GetTasksByProjectCase
import via.easyflow.interactor.usecases.cases.task.GetUserTasksInProjectCase
import via.easyflow.interactor.usecases.cases.user.UserIsAvailableCase
import via.easyflow.interactor.usecases.cases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.invoker.ProjectCaseInvoker
import via.easyflow.interactor.usecases.invoker.TaskCaseInvoker
import via.easyflow.interactor.usecases.invoker.UserCaseInvoker
import via.easyflow.shared.exceptions.exception.ForbiddenException
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.task.model.SubtaskModel
import via.easyflow.shared.modules.task.model.TaskCommentModel
import via.easyflow.shared.modules.task.model.TaskModel
import via.easyflow.shared.modules.task.model.flow.TaskInfoFlow

@Component
class TaskInteractor(
    // User
    private val userInvoker: UserCaseInvoker,
    // Project
    private val projectInvoker: ProjectCaseInvoker,
    // Task
    private val taskInvoker: TaskCaseInvoker,
) : ITaskInteractor {
    private val log = logger()

    override fun getTaskInfo(input: GetTaskInfoInteractorInput): Mono<TaskInfoFlow> {
        val taskMono: Mono<TaskModel> = taskInvoker.invoke(
            GetTaskByIdCase.Input(
                taskId = input.taskId
            )
        )

        val comments: Flux<TaskCommentModel> = taskInvoker.invoke(
            GetTaskCommentsCase.Input(
                taskId = input.taskId
            )
        )

        val subtasks: Flux<SubtaskModel> = taskInvoker.invoke(
            GetSubtaskCase.Input(
                taskId = input.taskId
            )
        )

        return taskMono.map { task ->
            TaskInfoFlow(
                taskId = task.taskId!!,
                taskName = task.name,
                ownerId = task.ownerUserId,
                status = task.status,
                comments = comments,
                subtasks = subtasks,
            )
        }
    }

    override fun addSubtask(input: AddSubtaskInteractorInput): Mono<SubtaskModel> {
        log.info("Starting to add subtask with title: '${input.title}' for task: ${input.taskId}")

        val ownerIsAvailable =
            userInvoker.invoke<UserMustBeAvailableCase, _, _>(UserMustBeAvailableCase.Input(input.ownerId))
                .doOnSubscribe { log.debug("Validating owner availability: ${input.ownerId}") }
                .doOnSuccess { log.debug("Owner ${input.ownerId} validated successfully") }
                .doOnError { log.error("Owner validation failed: ${it.message}") }

        val projectIsAvailableForOwner = projectInvoker.invoke<ProjectMustBeAvailableForUserCase, _, _>(
            ProjectMustBeAvailableForUserCase.Input(
                userId = input.ownerId,
                projectId = input.projectId
            )
        )
            .doOnSubscribe { log.debug("Validating project availability for owner: project=${input.projectId}, owner=${input.ownerId}") }
            .doOnSuccess { log.debug("Project ${input.projectId} is available for owner ${input.ownerId}") }
            .doOnError { log.error("Project availability validation failed for owner: ${it.message}") }

        val ownerValidated = ownerIsAvailable.zipWith(projectIsAvailableForOwner)
            .doOnSuccess { log.info("Owner and project validation completed successfully") }

        val assigneeNeeded = Mono.justOrEmpty(input.assigneeId)
            .doOnNext { log.debug("Assignee specified: $it") }

        val assigneeAvailable = assigneeNeeded.flatMap {
            log.debug("Validating assignee availability: $it")
            userInvoker.invoke<UserMustBeAvailableCase, _, _>(
                UserMustBeAvailableCase.Input(
                    userId = it
                )
            ).doOnSuccess { _ -> log.debug("Assignee $it validated successfully") }
                .doOnError { error -> log.error("Assignee validation failed: ${error.message}") }
        }

        val projectIsAvailableForAssignee = assigneeNeeded.flatMap {
            log.debug("Validating project availability for assignee: project=${input.projectId}, assignee=$it")
            projectInvoker.invoke<ProjectIsAvailableForUserCase, _, _>(
                ProjectIsAvailableForUserCase.Input(
                    userId = it,
                    projectId = input.projectId
                )
            ).doOnSuccess { _ -> log.debug("Project ${input.projectId} is available for assignee $it") }
                .doOnError { error -> log.error("Project availability validation failed for assignee: ${error.message}") }
        }

        val assigneeValidated: Mono<Unit> = (assigneeAvailable.zipWith(projectIsAvailableForAssignee))
            .map {}
            .doOnSuccess { log.info("Assignee and project validation completed successfully") }
            .doOnError { log.error("Assignee validation failed: ${it.message}") }

        val validatedStream = ownerValidated.zipWith(assigneeValidated.switchIfEmpty(just(Unit)))
            .doOnSuccess { log.info("All validations completed successfully") }

        return validatedStream.flatMap {
            log.info("Creating subtask with title: '${input.title}' for task: ${input.taskId}")
            taskInvoker.invoke<AddSubtaskCase, _, _>(
                AddSubtaskCase.Input(
                    ownerId = input.ownerId,
                    taskId = input.taskId,
                    assigneeId = input.assigneeId,
                    description = input.description,
                    title = input.title,
                )
            ).doOnSuccess { subtask -> log.info("Subtask created successfully with id: ${subtask.subtaskId}") }
                .doOnError { error -> log.error("Failed to create subtask: ${error.message}") }
        }
    }

    override fun getTasksByProject(input: GetTasksByProjectInteractorInput): Flux<TaskModel> {
        return taskInvoker.invoke(
            GetTasksByProjectCase.Input(
                projectId = input.projectId
            )
        )
    }

    override fun addTaskComment(input: AddTaskCommentInteractorInput): Mono<TaskCommentModel> {
        val userAvailability: Mono<Unit> = userInvoker.invoke(
            UserMustBeAvailableCase.Input(
                userId = input.userId
            ),
        )
        val foundedTask: Mono<TaskModel> = taskInvoker.invoke(GetTaskByIdCase.Input(input.taskId))

        return userAvailability.zipWith(foundedTask)
            .flatMap {
                projectInvoker.invoke<ProjectMustBeAvailableForUserCase, _, _>(
                    ProjectMustBeAvailableForUserCase.Input(
                        userId = input.userId,
                        projectId = it.t2.projectId,
                    ),
                )
            }.flatMap {
                taskInvoker.invoke(
                    AddTaskCommentCase.Input(
                        taskId = input.taskId,
                        userId = input.userId,
                        comment = input.comment,
                    )
                )
            }
    }

    override fun changeTask(input: ChangeTaskInteractorInput): Mono<TaskModel> {
        val projectValidated = projectInvoker.invoke<ProjectMustBeAvailableForUserCase, _, _>(
            ProjectMustBeAvailableForUserCase.Input(
                userId = input.changerUserId,
                projectId = input.projectId,
            )
        )
        val userValidated = userInvoker.invoke<UserMustBeAvailableCase, _, _>(
            input = UserMustBeAvailableCase.Input(
                userId = input.changerUserId
            )
        )

        return projectValidated.zipWith(userValidated)
            .flatMap {
                taskInvoker.invoke(
                    ChangeTaskCase.Input(
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
        val projectIsAvailableValidated = projectInvoker.invoke<ProjectIsAvailableForUserCase, _, _>(
            ProjectIsAvailableForUserCase.Input(
                userId = input.userId,
                projectId = input.projectId,
            )
        ).flatMap {
            if (it) just(it)
            else error(ForbiddenException("User tasks not available"))
        }

        val userIsAvailableValidated = userInvoker.invoke<UserMustBeAvailableCase, _, _>(
            UserMustBeAvailableCase.Input(
                userId = input.userId
            )
        )

        return projectIsAvailableValidated.zipWith(userIsAvailableValidated)
            .thenMany(
                taskInvoker.invoke(
                    GetUserTasksInProjectCase.Input(
                        projectId = input.projectId,
                        userId = input.userId
                    )
                )
            )
    }

    override fun addTask(input: AddTaskInteractorInput): Mono<TaskModel> {
        val userCaseInput = UserIsAvailableCase.Input(
            userId = input.userId
        )

        val projectInput = ProjectIsAvailableForUserCase.Input(
            userId = input.userId,
            projectId = input.projectId
        )

        val addTaskInput = AddTaskCase.Input(
            taskName = input.taskName,
            taskDescription = input.taskDescription,
            userId = input.userId,
            projectId = input.projectId,
        )

        val userAvailableMono =
            userInvoker.invoke<_, UserIsAvailableCase.Input, Mono<Boolean>>(userCaseInput).flatMap {
                if (it) {
                    just(it)
                } else {
                    error(NotFoundException("Can't find user ${input.userId}"))
                }
            }

        val projectAvailableMono = projectInvoker.invoke<ProjectIsAvailableForUserCase, _, _>(projectInput)
            .flatMap {
                if (it) just(it)
                else error(NotFoundException("Can't find project"))
            }

        val validated = userAvailableMono.zipWith(projectAvailableMono).then()

        val addedTask: Mono<TaskModel> = validated.then(Mono.defer { taskInvoker.invoke(addTaskInput) })

        return addedTask
    }
}