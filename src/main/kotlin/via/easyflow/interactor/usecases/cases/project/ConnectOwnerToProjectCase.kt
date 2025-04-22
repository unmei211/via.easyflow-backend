package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersIn
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.model.ProjectMemberModel

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