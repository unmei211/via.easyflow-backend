package via.easyflow.modules.auth.api.interaction.service.user

import reactor.core.publisher.Mono
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.contract.`in`.user.UpsertUserIn
import via.easyflow.modules.auth.api.model.user.UserModel

interface IUserService {
    fun existsUser(existsUserIn: ExistsUserIn): Mono<Boolean>
    fun upsertUser(upsertUserIn: UpsertUserIn): Mono<UserModel>
}