package via.easyflow.shared.modules.task.api.inputs

import via.easyflow.shared.modules.task.api.inputs.view.ChangeTaskView

data class ChangeTaskIn(
    val task: ChangeTaskView,
    val taskId: String,
    val changerId: String,
    val version: String
) {
}