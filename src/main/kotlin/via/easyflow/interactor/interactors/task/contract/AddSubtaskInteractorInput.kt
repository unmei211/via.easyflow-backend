package via.easyflow.interactor.interactors.task.contract

data class AddSubtaskInteractorInput(
    val ownerId: String,
    val taskId: String,
    val projectId: String,
    val assigneeId: String? = null,
    val description: String? = null,
    val title: String
)