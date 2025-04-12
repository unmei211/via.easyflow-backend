package via.easyflow.interactor.usecases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.auth.api.contract.`in`.user.UpsertUserIn
import via.easyflow.modules.auth.api.interaction.service.user.IUserService
import via.easyflow.modules.auth.api.model.user.UserModel

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