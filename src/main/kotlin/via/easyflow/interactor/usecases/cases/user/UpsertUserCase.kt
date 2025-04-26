package via.easyflow.interactor.usecases.cases.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.interactor.usecases.cases.user.UpsertUserCase.Input
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.api.service.IUserService
import via.easyflow.shared.modules.auth.model.user.UserModel
import kotlin.reflect.KClass

@Component
@Case(scope = CaseScope.USER)
class UpsertUserCase(
    private val userService: IUserService,
) : TypedUseCase<Input, Mono<UserModel>> {
    override fun invoke(input: Input): Mono<UserModel> {
        return userService.upsertUser(
            upsertUserIn = UpsertUserIn(
                userId = input.userId,
                name = input.name,
            )
        )
    }

    override fun getInputType(): KClass<Input> = Input::class
    data class Input(
        val userId: String?,
        val name: String
    )
}
