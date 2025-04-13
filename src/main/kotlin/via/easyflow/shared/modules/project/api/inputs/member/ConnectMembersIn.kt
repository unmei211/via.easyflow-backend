package via.easyflow.shared.modules.project.api.inputs.member

data class ConnectMembersIn(
    var projectId: String,
    var userIds: List<String>
)