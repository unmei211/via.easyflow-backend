package via.easyflow.modules.auth.internal.entity

import java.time.LocalDateTime

data class UserEntity(
    val userId: String,
    val name: String,
    val createdAt: LocalDateTime,
) {
}