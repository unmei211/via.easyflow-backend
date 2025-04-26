package via.easyflow.interactor.interactors.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.interactor.usecases.cases.user.UpsertUserCase
import via.easyflow.interactor.usecases.invoker.UserCaseInvoker
import via.easyflow.shared.modules.auth.model.user.UserModel

@Component
class UserInteractor(
    private val userInvoker: UserCaseInvoker,
) : IUserInteractor {
    override fun upsertUser(input: UpsertUserInteractorInput): Mono<UserModel> {
        val result: Mono<UserModel> = userInvoker.invoke(
            input = UpsertUserCase.Input(
                userId = input.userId,
                name = input.name,
            )
        )
        return result
    }
}