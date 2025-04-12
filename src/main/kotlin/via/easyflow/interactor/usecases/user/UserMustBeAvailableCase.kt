package via.easyflow.interactor.usecases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.core.exception.NotFoundException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.interaction.service.user.IUserService

@Component
class UserMustBeAvailableCase(
    private val userService: IUserService,
) : UseCase<UserMustBeAvailableUseCaseInput, Mono<Unit>> {
    override fun invoke(input: UserMustBeAvailableUseCaseInput): Mono<Unit> {
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
}

data class UserMustBeAvailableUseCaseInput(
    val userId: String
)