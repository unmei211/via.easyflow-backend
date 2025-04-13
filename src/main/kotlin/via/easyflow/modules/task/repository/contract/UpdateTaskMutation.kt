package via.easyflow.modules.task.repository.contract

import via.easyflow.modules.task.repository.model.TaskEntity

data class UpdateTaskMutation(
    val taskEntity: TaskEntity,
    val currentVersion: String,
) {
}