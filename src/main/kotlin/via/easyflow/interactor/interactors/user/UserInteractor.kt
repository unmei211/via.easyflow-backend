package via.easyflow.interactor.interactors.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.interactor.usecases.cases.user.typed.UpsertUserCaseInput
import via.easyflow.interactor.usecases.manager.IUseCaseManager
import via.easyflow.shared.modules.auth.model.user.UserModel

@Component
class UserInteractor(
    private val userUserCaseManager: IUseCaseManager
) : IUserInteractor {
    override fun upsertUser(input: UpsertUserInteractorInput): Mono<UserModel> {
        val result: Mono<UserModel> = userUserCaseManager.invoke(
            input = UpsertUserCaseInput(
                userId = input.userId,
                name = input.name,
            )
        )
        return result;
    }
}