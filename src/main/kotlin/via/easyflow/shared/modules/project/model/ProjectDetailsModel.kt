package via.easyflow.shared.modules.project.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ProjectDetailsModel(
    var projectId: String,
    var name: String,
    var description: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    var createdAt: LocalDateTime,

    var members: List<ProjectMemberModel>,
    var owner: ProjectOwnerModel,
)