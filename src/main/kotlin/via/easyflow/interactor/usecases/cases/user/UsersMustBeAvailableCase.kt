package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService

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