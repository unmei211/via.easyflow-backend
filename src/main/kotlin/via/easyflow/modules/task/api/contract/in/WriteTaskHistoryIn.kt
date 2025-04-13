package via.easyflow.modules.task.api.contract.`in`

import via.easyflow.modules.task.api.model.base.TaskModel

data class WriteTaskHistoryIn(
    val task: TaskModel,
    val changerId: String
) {
}