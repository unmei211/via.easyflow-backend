package via.easyflow.shared.modules.task.api.inputs

data class GetTasksByProjectIn(
    val projectId: String,
    val limit: Int? = 10
) {
}