package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersIn
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.model.ProjectMemberModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.PROJECT)
class ConnectOwnerToProjectCase(
    private val projectMemberService: IProjectMemberService
) : TypedUseCase<ConnectOwnerToProjectCase.Input, Mono<ProjectMemberModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Mono<ProjectMemberModel> {
        return projectMemberService.connectMembers(
            ConnectMembersIn(
                projectId = input.projectId,
                userIds = listOf(input.userId),
            )
        ).single()
    }

    data class Input(
        var projectId: String,
        var userId: String
    )
}