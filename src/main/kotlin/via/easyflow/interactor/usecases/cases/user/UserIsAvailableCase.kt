package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService

@Component
class UserIsAvailableCase(
    private val userService: IUserService,
) : UseCase<UserIsAvailableUseCaseInput, Mono<Boolean>> {
    override fun invoke(input: UserIsAvailableUseCaseInput): Mono<Boolean> {
        val exists: Mono<Boolean> = userService.existsUser(
            ExistsUserIn(
                userId = input.userId
            )
        )
        return exists
    }
}

data class UserIsAvailableUseCaseInput(
    val userId: String
)