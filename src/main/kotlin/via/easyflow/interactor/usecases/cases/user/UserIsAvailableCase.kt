package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.interactor.usecases.cases.user.UserIsAvailableCase.Input
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import kotlin.reflect.KClass

@Component
@Case(CaseScope.USER)
class UserIsAvailableCase(
    private val userService: IUserService,
) : TypedUseCase<Input, Mono<Boolean>> {
    override fun invoke(input: Input): Mono<Boolean> {
        val exists: Mono<Boolean> = userService.existsUser(
            ExistsUserIn(
                userId = input.userId
            )
        )
        return exists
    }

    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    data class Input(
        val userId: String
    )
}