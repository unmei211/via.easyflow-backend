package via.easyflow.modules.task.internal.repository.contract

import via.easyflow.modules.task.internal.repository.model.TaskEntity

data class UpdateTaskMutation(
    val taskEntity: TaskEntity,
    val currentVersion: String,
) {
}