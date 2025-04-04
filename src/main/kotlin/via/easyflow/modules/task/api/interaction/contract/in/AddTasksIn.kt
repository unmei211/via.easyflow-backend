package via.easyflow.modules.task.api.interaction.contract.`in`

import org.springframework.scheduling.config.Task

data class AddTasksIn(
    val tasks: List<Task>,
    val projectId: String,
) {
}