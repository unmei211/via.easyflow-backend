package via.easyflow.modules.project.model.exchange.`in`

data class ConnectMembersIn(
    var projectId: String,
    var userIdRoleIdMap: Map<String, List<String>>
)