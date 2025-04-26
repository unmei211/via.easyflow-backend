package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import kotlin.reflect.KClass

@Component
@Case(CaseScope.USER)
class UserMustBeAvailableCase(
    private val userService: IUserService,
) : TypedUseCase<UserMustBeAvailableCase.Input, Mono<Unit>> {
    override fun invoke(input: Input): Mono<Unit> {
        val exists: Mono<Boolean> = userService.existsUser(
            ExistsUserIn(
                userId = input.userId
            )
        )
        return exists.flatMap {
            if (it) {
                just(Unit)
            } else {
                error(NotFoundException("Can't fnd user"))
            }
        }
    }

    override fun getInputType(): KClass<Input> = Input::class

    data class Input(
        val userId: String
    )
}
