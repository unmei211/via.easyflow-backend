package via.easyflow.modules.task.api.interaction.service

import org.springframework.scheduling.config.Task
import via.easyflow.modules.task.api.interaction.contract.`in`.AddTasksIn
import via.easyflow.modules.task.api.interaction.contract.`in`.GetTasksByProjectIn
import via.easyflow.modules.task.api.interaction.contract.`in`.GetTasksByUserIn

interface ITaskService {
    fun addTasks(addTasksIn: AddTasksIn)
    fun getTasksByUser(getTasksByUserIn: GetTasksByUserIn): List<Any>
    fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn)
    fun changeTask(changeTaskIn: ChangeTaskIn)
}