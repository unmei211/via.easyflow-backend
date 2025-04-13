package via.easyflow.interactor.adapter.user

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.model.user.UserModel

class UserServiceAdapter(
    private val webClient: WebClient,
) : IUserServiceAdapter {
    override fun existsUser(existsUserIn: ExistsUserIn): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun upsertUser(upsertUserIn: UpsertUserIn): Mono<UserModel> {
        TODO("Not yet implemented")
    }
}