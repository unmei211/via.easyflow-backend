package via.easyflow.interactor.interactors.project.contract

data class ConnectProjectMembersInteractorInput(
    val projectId: String,
    val userIds: List<String>,
) {
}