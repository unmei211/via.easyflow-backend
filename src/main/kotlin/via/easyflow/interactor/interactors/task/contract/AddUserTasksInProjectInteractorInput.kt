package via.easyflow.interactor.interactors.task.contract

data class AddUserTasksInProjectInteractorInput(
    val userId: String,
    val projectId: String,
) {
}