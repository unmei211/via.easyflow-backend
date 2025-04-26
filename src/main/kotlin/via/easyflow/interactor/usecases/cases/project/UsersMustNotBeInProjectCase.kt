package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.exceptions.exception.ConflictException
import via.easyflow.shared.modules.project.api.service.IProjectMemberService
import via.easyflow.shared.modules.project.api.service.UserExistsInProjectModuleInput
import kotlin.reflect.KClass

@Component
@Case(CaseScope.PROJECT)
class UsersMustNotBeInProjectCase(
    private val projectMemberService: IProjectMemberService,
) : TypedUseCase<UsersMustNotBeInProjectCase.Input, Mono<Unit>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Mono<Unit> {
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

    data class Input(
        val projectId: String,
        val userIds: List<String>
    ) {

    }
}
