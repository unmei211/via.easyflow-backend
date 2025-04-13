package via.easyflow.modules.task.api.contract.`in`

import via.easyflow.modules.task.api.contract.`in`.view.ChangeTaskView

data class ChangeTaskIn(
    val task: ChangeTaskView,
    val taskId: String,
    val changerId: String,
    val version: String
) {
}