package via.easyflow.interactor.interactors.project.contract

data class CreateProjectInteractorInput(
    val userId: String,
    val projectName: String,
    val projectDescription: String
) {
}