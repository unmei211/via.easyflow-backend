package via.easyflow.interactor.interactors.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
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
import via.easyflow.interactor.usecases.cases.project.ProjectIsAvailableForUserCaseInput
import via.easyflow.interactor.usecases.cases.project.ProjectMustBeAvailableForUserCase
import via.easyflow.interactor.usecases.cases.project.ProjectMustBeAvailableForUserCaseInput
import via.easyflow.interactor.usecases.cases.task.AddSubtaskCase
import via.easyflow.interactor.usecases.cases.task.AddTaskCase
import via.easyflow.interactor.usecases.cases.task.AddTaskCaseInput
import via.easyflow.interactor.usecases.cases.task.AddTaskCommentCase
import via.easyflow.interactor.usecases.cases.task.ChangeTaskCase
import via.easyflow.interactor.usecases.cases.task.ChangeTaskCaseInput
import via.easyflow.interactor.usecases.cases.task.GetSubtaskCase
import via.easyflow.interactor.usecases.cases.task.GetTaskByIdCase
import via.easyflow.interactor.usecases.cases.task.GetTaskCommentsCase
import via.easyflow.interactor.usecases.cases.task.GetTasksByProjectCase
import via.easyflow.interactor.usecases.cases.task.GetUserTasksInProjectCase
import via.easyflow.interactor.usecases.cases.task.GetUserTasksInProjectCaseInput
import via.easyflow.interactor.usecases.cases.user.UserIsAvailableCase
import via.easyflow.interactor.usecases.cases.user.UserIsAvailableUseCaseInput
import via.easyflow.interactor.usecases.cases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.cases.user.UserMustBeAvailableUseCaseInput
import via.easyflow.shared.exceptions.exception.ForbiddenException
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.task.model.SubtaskModel
import via.easyflow.shared.modules.task.model.TaskCommentModel
import via.easyflow.shared.modules.task.model.TaskModel
import via.easyflow.shared.modules.task.model.flow.TaskInfoFlow

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
    private val getTasksByProjectCase: GetTasksByProjectCase,
    private val addSubtaskCase: AddSubtaskCase,
    private val getTaskCommentsCase: GetTaskCommentsCase,
    private val getSubtasksCase: GetSubtaskCase
) : ITaskInteractor {
    private val log = logger()

    override fun getTaskInfo(input: GetTaskInfoInteractorInput): Mono<TaskInfoFlow> {
        val taskMono = getTaskByIdCase.invoke(
            GetTaskByIdCase.Input(
                taskId = input.taskId
            )
        )

        val comments = getTaskCommentsCase.invoke(
            GetTaskCommentsCase.Input(
                taskId = input.taskId
            )
        )

        val subtasks = getSubtasksCase.invoke(
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

        val ownerIsAvailable = userMustBeAvailableCase.invoke(UserMustBeAvailableUseCaseInput(input.ownerId))
            .doOnSubscribe { log.debug("Validating owner availability: ${input.ownerId}") }
            .doOnSuccess { log.debug("Owner ${input.ownerId} validated successfully") }
            .doOnError { log.error("Owner validation failed: ${it.message}") }

        val projectIsAvailableForOwner = projectMustBeAvailableForUserCase.invoke(
            ProjectMustBeAvailableForUserCaseInput(
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
            userMustBeAvailableCase.invoke(
                UserMustBeAvailableUseCaseInput(
                    userId = it
                )
            ).doOnSuccess { _ -> log.debug("Assignee $it validated successfully") }
                .doOnError { error -> log.error("Assignee validation failed: ${error.message}") }
        }

        val projectIsAvailableForAssignee = assigneeNeeded.flatMap {
            log.debug("Validating project availability for assignee: project=${input.projectId}, assignee=$it")
            projectIsAvailableForUserCase.invoke(
                ProjectIsAvailableForUserCaseInput(
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
            addSubtaskCase.invoke(
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
                    ProjectMustBeAvailableForUserCaseInput(
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
            ProjectMustBeAvailableForUserCaseInput(
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