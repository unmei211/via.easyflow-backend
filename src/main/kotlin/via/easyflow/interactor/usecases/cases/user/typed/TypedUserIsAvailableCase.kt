package via.easyflow.interactor.usecases.cases.user.typed

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import kotlin.reflect.KClass

@Component
@Case(CaseScope.USER)
class TypedUserIsAvailableCase(
    private val userService: IUserService,
) : TypedUseCase<UserIsAvailableUseCaseInput, Mono<Boolean>> {
    override fun invoke(input: UserIsAvailableUseCaseInput): Mono<Boolean> {
        val exists: Mono<Boolean> = userService.existsUser(
            ExistsUserIn(
                userId = input.userId
            )
        )
        return exists
    }

    override fun getInputType(): KClass<UserIsAvailableUseCaseInput> {
        return UserIsAvailableUseCaseInput::class
    }
}

data class UserIsAvailableUseCaseInput(
    val userId: String
)