package via.easyflow.interactor.interactors.task.contract

data class AddTaskCommentInteractorInput(
    val comment: String,
    val taskId: String,
    val userId: String,
) {
}