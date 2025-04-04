package via.easyflow.modules.project.api.contract.`in`.member

data class ConnectMembersViaRolesIn(
    var projectId: String,
    var userToRoles: Map<String, List<String>>
)