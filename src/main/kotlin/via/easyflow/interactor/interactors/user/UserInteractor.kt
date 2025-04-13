package via.easyflow.interactor.interactors.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.interactor.usecases.user.UpsertUserCase
import via.easyflow.interactor.usecases.user.UpsertUserCaseInput
import via.easyflow.shared.modules.auth.model.user.UserModel

@Component
class UserInteractor(
    private val upsertUserCase: UpsertUserCase,
) : IUserInteractor {
    override fun upsertUser(input: UpsertUserInteractorInput): Mono<UserModel> {
        return upsertUserCase.invoke(
            input = UpsertUserCaseInput(
                userId = input.userId,
                name = input.name,
            )
        )
    }
}