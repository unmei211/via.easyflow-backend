package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.project.api.contract.`in`.member.GetProjectMembersModuleIn
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService
import via.easyflow.modules.project.api.model.ProjectMemberModel

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