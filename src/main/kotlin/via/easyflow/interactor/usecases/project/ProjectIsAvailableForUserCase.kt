package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService
import via.easyflow.modules.project.api.interaction.service.member.UserExistsInProjectModuleInput
import via.easyflow.modules.project.api.interaction.service.project.IProjectService
import via.easyflow.modules.project.api.interaction.service.role.IProjectRoleService

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