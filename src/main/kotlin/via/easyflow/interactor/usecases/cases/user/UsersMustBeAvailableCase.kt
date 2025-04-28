package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import kotlin.reflect.KClass

@Component
@Case(CaseScope.USER)
class UsersMustBeAvailableCase(
    private val userService: IUserService
) : TypedUseCase<UsersMustBeAvailableCase.Input, Mono<Unit>> {
    override fun invoke(input: Input): Mono<Unit> {
        return Flux
            .fromIterable(input.userIds)
            .flatMap {
                userService.existsUser(
                    ExistsUserIn(
                        userId = it
                    )
                ).flatMap { exists ->
                    if (exists) just(it)
                    else error(NotFoundException("User Not Found"))
                }
            }.then(just(Unit))
    }

    override fun getInputType(): KClass<Input> = Input::class

    data class Input(
        val userIds: List<String>
    )
}