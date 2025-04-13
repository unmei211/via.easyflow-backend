package via.easyflow.shared.modules.task.api.inputs

class GetTasksByUserIn(
    val userId: String,
    val projectId: String,
    val limit: Long? = 10
) {

}