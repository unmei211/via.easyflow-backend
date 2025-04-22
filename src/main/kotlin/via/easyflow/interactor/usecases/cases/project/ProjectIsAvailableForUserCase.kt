package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.api.service.UserExistsInProjectModuleInput
import via.easyflow.shared.modules.project.api.service.IProjectService
import via.easyflow.shared.modules.project.api.service.IProjectRoleService

@Component
class ProjectIsAvailableForUserCase(
    private val projectService: IProjectService,
    private val projectMemberService: IProjectMemberService,
    private val projectRoleService: IProjectRoleService,
) : UseCase<ProjectIsAvailableForUserCaseInput, Mono<Boolean>> {
    override fun invoke(input: ProjectIsAvailableForUserCaseInput): Mono<Boolean> {
        val existsMono = projectMemberService.userExistsInProject(
            UserExistsInProjectModuleInput(
                userId = input.userId,
                projectId = input.projectId,
            )
        )
        return existsMono
    }
}

data class ProjectIsAvailableForUserCaseInput(
    val userId: String,
    val projectId: String
)