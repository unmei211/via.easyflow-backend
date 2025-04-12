package via.easyflow.interactor.usecases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.core.exception.NotFoundException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.interaction.service.user.IUserService

@Component
class UsersMustBeAvailableCase(
    private val userService: IUserService
) : UseCase<UsersMustBeAvailableCaseInput, Mono<Unit>> {
    override fun invoke(input: UsersMustBeAvailableCaseInput): Mono<Unit> {
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
}

data class UsersMustBeAvailableCaseInput(
    val userIds: List<String>
)