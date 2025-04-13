package via.easyflow.interactor.usecases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService

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