package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.project.api.inputs.member.GetProjectMembersModuleIn
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.model.ProjectMemberModel

@Component
class GetProjectMembersCase(
    private val projectMemberService: IProjectMemberService,
) : UseCase<GetProjectMembersCaseInput, Flux<ProjectMemberModel>> {
    override fun invoke(input: GetProjectMembersCaseInput): Flux<ProjectMemberModel> {
        return projectMemberService.getProjectMembers(
            GetProjectMembersModuleIn(
                projectId = input.projectId
            ),
        )
    }
}

data class GetProjectMembersCaseInput(
    val projectId: String,
)