package via.easyflow.shared.modules.project.api.inputs.member

data class ConnectMembersViaRolesIn(
    var projectId: String,
    var userToRoles: Map<String, List<String>>
)