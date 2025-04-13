package via.easyflow.modules.auth.service.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.model.user.UserModel
import via.easyflow.modules.auth.repository.user.model.UserEntity
import via.easyflow.modules.auth.repository.user.IUserRepository
import via.easyflow.modules.auth.repository.user.contract.enquiry.UpsertUserEnquiry
import via.easyflow.modules.auth.repository.user.contract.query.FindByIdUserQuery
import via.easyflow.shared.modules.auth.api.service.IUserService
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
            log.debug("Upserted user [{}]", it.name)
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
