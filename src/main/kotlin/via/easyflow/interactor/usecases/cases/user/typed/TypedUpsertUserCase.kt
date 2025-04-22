package via.easyflow.interactor.usecases.cases.user.typed

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.interactor.usecases.annotation.UseCase
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import via.easyflow.shared.modules.auth.model.user.UserModel
import kotlin.reflect.KClass

@Component
@UseCase(scope = CaseScope.USER)
class TypedUpsertUserCase(
    private val userService: IUserService,
) : TypedUseCase<UpsertUserCaseInput, Mono<UserModel>> {
    override fun invoke(input: UpsertUserCaseInput): Mono<UserModel> {
        return userService.upsertUser(
            upsertUserIn = UpsertUserIn(
                userId = input.userId,
                name = input.name,
            )
        )
    }

    override fun getInputType(): KClass<UpsertUserCaseInput> = UpsertUserCaseInput::class
}

data class UpsertUserCaseInput(
    val userId: String?,
    val name: String
)