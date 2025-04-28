package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.project.api.inputs.member.GetProjectMembersModuleIn
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.model.ProjectMemberModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.PROJECT)
class GetProjectMembersCase(
    private val projectMemberService: IProjectMemberService,
) : TypedUseCase<GetProjectMembersCase.Input, Flux<ProjectMemberModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Flux<ProjectMemberModel> {
        return projectMemberService.getProjectMembers(
            GetProjectMembersModuleIn(
                projectId = input.projectId
            ),
        )
    }

    data class Input(
        val projectId: String,
    )
}
