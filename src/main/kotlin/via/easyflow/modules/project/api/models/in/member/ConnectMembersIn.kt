package via.easyflow.modules.project.api.models.`in`.member

data class ConnectMembersIn(
    var projectId: String,
    var userToRoles: Map<String, List<String>>
)