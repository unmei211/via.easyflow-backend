package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.core.exception.ConflictException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService
import via.easyflow.modules.project.api.model.ProjectMemberModel

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