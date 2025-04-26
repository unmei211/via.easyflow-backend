package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.exceptions.exception.ForbiddenException
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.api.service.IProjectRoleService
import via.easyflow.shared.modules.project.api.service.IProjectService
import via.easyflow.shared.modules.project.api.service.UserExistsInProjectModuleInput
import kotlin.reflect.KClass

@Component
@Case(CaseScope.PROJECT)
class ProjectMustBeAvailableForUserCase(
    private val projectService: IProjectService,
    private val projectMemberService: IProjectMemberService,
    private val projectRoleService: IProjectRoleService,
) : TypedUseCase<ProjectMustBeAvailableForUserCase.Input, Mono<Boolean>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Mono<Boolean> {
        val existsMono = projectMemberService.userExistsInProject(
            UserExistsInProjectModuleInput(
                userId = input.userId,
                projectId = input.projectId,
            )
        ).flatMap {
            if (it) Mono.just(it)
            else Mono.error(ForbiddenException("User must be in project"))
        }
        return existsMono
    }

    data class Input(
        val userId: String,
        val projectId: String
    )
}