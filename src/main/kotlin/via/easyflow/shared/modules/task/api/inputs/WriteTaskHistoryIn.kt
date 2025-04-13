package via.easyflow.shared.modules.task.api.inputs

import via.easyflow.shared.modules.task.model.TaskModel

data class WriteTaskHistoryIn(
    val task: TaskModel,
    val changerId: String
) {
}