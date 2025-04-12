package via.easyflow.interactor.interactors.task.contract

data class AddTaskInteractorInput(
    val taskName: String,
    val taskDescription: String,
    val userId: String,
    val projectId: String,
) {
}