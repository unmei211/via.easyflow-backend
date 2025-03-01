package via.easyflow.modules.auth.api.model.user

import java.time.LocalDateTime

data class UserModel(
    val userId: String?,
    val name: String,
    val createdAt: LocalDateTime,
) {
}