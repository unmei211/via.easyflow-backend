package via.easyflow.modules.auth.api.interaction.service.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.contract.`in`.user.UpsertUserIn
import via.easyflow.modules.auth.api.model.user.UserModel
import via.easyflow.modules.auth.internal.entity.UserEntity
import via.easyflow.modules.auth.internal.repository.user.IUserRepository
import via.easyflow.modules.auth.internal.repository.user.contract.enquiry.UpsertUserEnquiry
import via.easyflow.modules.auth.internal.repository.user.contract.query.FindByIdUserQuery
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: IUserRepository,
) : IUserService {
    private val log = logger()

    override fun upsertUser(upsertUserIn: UpsertUserIn): Mono<UserModel> {
        return userRepository.upsert(
            UpsertUserEnquiry(
                UserEntity(
                    name = upsertUserIn.name,
                    createdAt = LocalDateTime.now(),
                    userId = upsertUserIn.userId ?: uuid()
                )
            )
        ).map {
            UserModel(
                userId = it.userId,
                name = it.name,
                createdAt = it.createdAt,
            )
        }
    }

    override fun existsUser(existsUserIn: ExistsUserIn): Mono<Boolean> {
        return userRepository.existsById(
            FindByIdUserQuery(
                userId = existsUserIn.userId
            )
        )
    }
}
