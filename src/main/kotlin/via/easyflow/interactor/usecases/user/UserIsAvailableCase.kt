package via.easyflow.interactor.usecases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.interaction.service.user.IUserService

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