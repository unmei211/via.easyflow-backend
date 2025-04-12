package via.easyflow.modules.task.internal.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.exception.NotFoundException
import via.easyflow.core.tools.database.query.resolver.IQueryFiltersResolver
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.core.tools.version.comparator.IVersionComparator
import via.easyflow.core.tools.version.versionizer.IVersionizer
import via.easyflow.modules.task.api.contract.`in`.*
import via.easyflow.modules.task.api.interaction.service.ITaskHistoryService
import via.easyflow.modules.task.api.interaction.service.ITaskService
import via.easyflow.modules.task.api.model.base.TaskModel
import via.easyflow.modules.task.internal.repository.contract.UpdateTaskMutation
import via.easyflow.modules.task.internal.repository.repository.task.ITaskRepository
import via.easyflow.modules.task.internal.service.filter.user_task.model.UserTaskFilterModel
import java.sql.Timestamp
import java.time.Instant

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

    override fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn) {
        TODO("Not yet implemented")
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
                taskHistoryService.writeTaskHistory(WriteTaskHistoryIn(task = prev))
                    .map { prev to updated }
            }
            .map { (prev, updated) -> TaskModel.from(updated) }
    }
}