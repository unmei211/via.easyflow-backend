package via.easyflow.interactor.interactors.task.contract

data class ChangeTaskInteractorInput(
    val version: String,
    val description: String,
    val status: String,
    val name: String,
    val changerUserId: String,
    val projectId: String,
    val taskId: String
)