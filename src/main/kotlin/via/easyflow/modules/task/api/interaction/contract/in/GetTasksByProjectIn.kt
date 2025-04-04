package via.easyflow.modules.task.api.interaction.contract.`in`

data class GetTasksByProjectIn(
    val projectId: String,
    val limit: Int?
) {
}