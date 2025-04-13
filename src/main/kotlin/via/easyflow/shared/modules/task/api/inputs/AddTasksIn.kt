package via.easyflow.shared.modules.task.api.inputs

import via.easyflow.shared.modules.task.api.inputs.view.CreateTaskView

data class AddTasksIn(
    val tasks: List<CreateTaskView>,
    val projectId: String,
    val ownerId: String,
) {
}