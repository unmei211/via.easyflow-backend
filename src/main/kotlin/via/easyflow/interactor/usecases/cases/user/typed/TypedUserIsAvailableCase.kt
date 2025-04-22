package via.easyflow.interactor.usecases.cases.user.typed

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import kotlin.reflect.KClass

@Component
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
        TODO("Not yet implemented")
    }
}

data class UserIsAvailableUseCaseInput(
    val userId: String
)