package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersIn
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.model.ProjectMemberModel

@Component
class ConnectProjectMembersCase(
    private val projectMemberService: IProjectMemberService,
) : UseCase<ConnectProjectMembersCaseInput, Flux<ProjectMemberModel>> {
    override fun invoke(input: ConnectProjectMembersCaseInput): Flux<ProjectMemberModel> {
        val connected =  projectMemberService.connectMembers(
            ConnectMembersIn(
                projectId = input.projectId,
                userIds = input.userIds
            )
        )
        return connected
    }
}

data class ConnectProjectMembersCaseInput(
    val projectId: String,
    val userIds: List<String>
) {

}