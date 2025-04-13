package via.easyflow.shared.modules.auth.api.service

import reactor.core.publisher.Mono
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.model.user.UserModel

interface IUserService {
    fun existsUser(existsUserIn: ExistsUserIn): Mono<Boolean>
    fun upsertUser(upsertUserIn: UpsertUserIn): Mono<UserModel>
}