package via.easyflow.modules.project.internal.entity

data class ProjectRoleEntity(
    var projectRoleId: String,
    var projectId: String,
    var name: String,
    var description: String?,
    var color: String
) {
}