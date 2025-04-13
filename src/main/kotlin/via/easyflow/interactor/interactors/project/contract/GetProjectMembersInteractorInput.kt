package via.easyflow.interactor.interactors.project.contract

data class GetProjectMembersInteractorInput(
    val projectId: String,
    val userId: String
) {
}