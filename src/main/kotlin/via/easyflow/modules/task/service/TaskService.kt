package via.easyflow.modules.task.service

import java.sql.Timestamp
import java.time.Instant
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.query.resolver.IQueryFiltersResolver
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.core.tools.version.comparator.IVersionComparator
import via.easyflow.core.tools.version.versionizer.IVersionizer
import via.easyflow.modules.task.repository.contract.UpdateTaskMutation
import via.easyflow.modules.task.repository.repository.task.ITaskRepository
import via.easyflow.modules.task.service.user_task.model.UserTaskFilterModel
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.task.api.inputs.AddTasksIn
import via.easyflow.shared.modules.task.api.inputs.ChangeTaskIn
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.inputs.GetTasksByProjectIn
import via.easyflow.shared.modules.task.api.inputs.GetTasksByUserIn
import via.easyflow.shared.modules.task.api.inputs.WriteTaskHistoryIn
import via.easyflow.shared.modules.task.api.service.ITaskHistoryService
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel

@Service
class TaskService(
    private val taskRepository: ITaskRepository,
    private val taskHistoryService: ITaskHistoryService,
    private val versionizer: IVersionizer<Timestamp>,
    private val versionComparator: IVersionComparator<Timestamp>,
    private val userTaskFilterResolver: IQueryFiltersResolver<UserTaskFilterModel>
) : ITaskService {
    override fun getTaskById(getTaskByIdIn: GetTaskByIdIn): Mono<TaskModel> {
        return taskRepository
            .getById(Mono.just(getTaskByIdIn.taskId))
            .switchIfEmpty(Mono.error(NotFoundException("Not found")))
            .map { TaskModel.from(it) }
    }

    override fun addTasks(addTasksIn: AddTasksIn): Flux<TaskModel> {
        val tasksFlux = Flux.fromIterable(addTasksIn.tasks)
        val projectId = addTasksIn.projectId
        val userId = addTasksIn.ownerId

        return tasksFlux
            .map { taskView ->
                TaskModel(
                    name = taskView.name,
                    taskId = uuid(),
                    description = taskView.description,
                    status = "TODO",
                    createdAt = Instant.now(),
                    updatedAt = null,
                    ownerUserId = userId,
                    projectId = projectId,
                    version = versionizer.go(),
                )
            }
            .concatMap { taskModel ->
                taskRepository.add(taskModel.toEntity())
            }.map { taskEntity ->
                TaskModel.from(taskEntity)
            }
    }

    override fun getTasksByUser(getTasksByUserIn: GetTasksByUserIn): Flux<TaskModel> {
        val filters = userTaskFilterResolver.resolve(
            UserTaskFilterModel(
                projectId = getTasksByUserIn.projectId,
                ownerUserId = getTasksByUserIn.userId
            )
        )

        return taskRepository.searchByFilter(filters, getTasksByUserIn.limit)
            .map { taskModel -> TaskModel.from(taskModel) }
    }

    override fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn): Flux<TaskModel> {
        val filters = userTaskFilterResolver.resolve(
            UserTaskFilterModel(
                projectId = getTasksByProjectIn.projectId,
            ),
        )

        return taskRepository.searchByFilter(filters).map { taskEntity -> TaskModel.from(taskEntity) }
    }

    override fun changeTask(changeTaskIn: ChangeTaskIn): Mono<TaskModel> {
        val existsTaskMono: Mono<TaskModel> = this.getTaskById(GetTaskByIdIn(taskId = changeTaskIn.taskId))
        val forUpdateMono = existsTaskMono
            .doOnNext { existsTask ->
                versionComparator.compare(changeTaskIn.version, existsTask.version)
            }
            .map { existsTask ->
                val viewTask = changeTaskIn.task
                val forUpdateTask = existsTask.copy(
                    name = viewTask.name,
                    taskId = changeTaskIn.taskId,
                    description = viewTask.description,
                    status = viewTask.status,
                    createdAt = existsTask.createdAt,
                    updatedAt = Instant.now(),
                    ownerUserId = existsTask.ownerUserId,
                    projectId = existsTask.projectId,
                    version = versionizer.go(),
                )
                existsTask to forUpdateTask
            }

        return forUpdateMono
            .flatMap { (prev, new) ->
                taskRepository.update(
                    UpdateTaskMutation(
                        taskEntity = new.toEntity(),
                        currentVersion = changeTaskIn.version,
                    )
                )
                    .map { prev to it }
            }
            .flatMap { (prev, updated) ->
                taskHistoryService.writeTaskHistory(
                    WriteTaskHistoryIn(
                        task = prev,
                        changerId = changeTaskIn.changerId,
                    )
                )
                    .map { prev to updated }
            }
            .map { (_, updated) -> TaskModel.from(updated) }
    }
}