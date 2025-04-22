package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import via.easyflow.shared.modules.auth.model.user.UserModel

@Component
class UpsertUserCase(
    private val userService: IUserService,
) : UseCase<UpsertUserCaseInput, Mono<UserModel>> {
    override fun invoke(input: UpsertUserCaseInput): Mono<UserModel> {
        return userService.upsertUser(
            upsertUserIn = UpsertUserIn(
                userId = input.userId,
                name = input.name,
            )
        )
    }
}

data class UpsertUserCaseInput(
    val userId: String?,
    val name: String
)