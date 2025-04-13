package via.easyflow.modules.auth.repository.user.model

import java.time.LocalDateTime

data class UserEntity(
    val userId: String,
    val name: String,
    val createdAt: LocalDateTime,
) {
}