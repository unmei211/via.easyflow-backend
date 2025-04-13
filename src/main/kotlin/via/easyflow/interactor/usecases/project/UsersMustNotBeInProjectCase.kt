package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import via.easyflow.shared.exceptions.exception.ConflictException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.api.service.UserExistsInProjectModuleInput

@Component
class UsersMustNotBeInProjectCase(
    private val projectMemberService: IProjectMemberService,
) : UseCase<UsersNotInProjectCaseInput, Mono<Unit>> {
    override fun invoke(input: UsersNotInProjectCaseInput): Mono<Unit> {
        val exists = Flux.fromIterable(input.userIds).flatMap {
            projectMemberService.userExistsInProject(
                UserExistsInProjectModuleInput(
                    userId = it,
                    projectId = input.projectId
                )
            ).flatMap { exists ->
                if (exists) error(ConflictException("Conflict"))
                else Mono.just(it)
            }
        }
        return exists.then(Mono.just(Unit))
    }
}

data class UsersNotInProjectCaseInput(
    val projectId: String,
    val userIds: List<String>
) {

}