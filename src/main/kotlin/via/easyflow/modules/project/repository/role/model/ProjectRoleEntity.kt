package via.easyflow.modules.project.repository.role.model

data class ProjectRoleEntity(
    var projectRoleId: String,
    var projectId: String,
    var name: String,
    var description: String?,
    var color: String
) {
}