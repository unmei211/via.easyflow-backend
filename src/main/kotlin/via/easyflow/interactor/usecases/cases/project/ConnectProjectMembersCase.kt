package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersIn
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.model.ProjectMemberModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.PROJECT)
class ConnectProjectMembersCase(
    private val projectMemberService: IProjectMemberService,
) : TypedUseCase<ConnectProjectMembersCase.Input, Flux<ProjectMemberModel>> {
    override fun invoke(input: Input): Flux<ProjectMemberModel> {
        val connected = projectMemberService.connectMembers(
            ConnectMembersIn(
                projectId = input.projectId,
                userIds = input.userIds
            )
        )
        return connected
    }

    data class Input(
        val projectId: String,
        val userIds: List<String>
    )

    override fun getInputType(): KClass<Input> {
        return Input::class
    }
}
