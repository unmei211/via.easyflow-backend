package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService
import via.easyflow.modules.project.api.model.ProjectMemberModel

@Component
class ConnectOwnerToProjectCase(
    private val projectMemberService: IProjectMemberService
) : UseCase<ConnectOwnerToProjectCaseInput, Mono<ProjectMemberModel>> {
    override fun invoke(input: ConnectOwnerToProjectCaseInput): Mono<ProjectMemberModel> {
        return projectMemberService.connectMembers(
            ConnectMembersIn(
                projectId = input.projectId,
                userIds = listOf(input.userId),
            )
        ).single()
    }
}

data class ConnectOwnerToProjectCaseInput(
    var projectId: String,
    var userId: String
)