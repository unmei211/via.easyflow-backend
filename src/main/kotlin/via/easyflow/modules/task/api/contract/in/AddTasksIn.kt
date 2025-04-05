package via.easyflow.modules.task.api.contract.`in`

import via.easyflow.modules.task.api.contract.`in`.view.CreateTaskView

data class AddTasksIn(
    val tasks: List<CreateTaskView>,
    val projectId: String,
    val ownerId: String,
) {
}