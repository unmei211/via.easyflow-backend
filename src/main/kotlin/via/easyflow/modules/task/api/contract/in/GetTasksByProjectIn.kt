package via.easyflow.modules.task.api.contract.`in`

data class GetTasksByProjectIn(
    val projectId: String,
    val limit: Int?
) {
}