package via.easyflow.modules.task.api.interaction.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.task.api.contract.`in`.*
import via.easyflow.modules.task.api.model.base.TaskModel
import via.easyflow.modules.task.internal.repository.repository.task.ITaskRepository
import java.time.Instant

@Service
class TaskService(
    private val taskRepository: ITaskRepository,
) : ITaskService {
    override fun getTaskById(getTaskByIdIn: GetTaskByIdIn): Mono<TaskModel> {
        return taskRepository.getById(Mono.just(getTaskByIdIn.taskId)).map { TaskModel.from(it) }
    }

    override fun addTasks(addTasksIn: AddTasksIn): Flux<TaskModel> {
        val tasksFlux = Flux.fromIterable(addTasksIn.tasks)
        val projectId = addTasksIn.projectId
        val userId = addTasksIn.projectId

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
                    projectId = projectId
                )
            }
            .concatMap { taskModel ->
                taskRepository.add(taskModel.toEntity())
            }.map { taskEntity ->
                TaskModel.from(taskEntity)
            }
    }

    override fun getTasksByUser(getTasksByUserIn: GetTasksByUserIn): List<Any> {
        TODO("Not yet implemented")
    }

    override fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn) {
        TODO("Not yet implemented")
    }

    override fun changeTask(changeTaskIn: ChangeTaskIn) {
        TODO("Not yet implemented")
    }
}