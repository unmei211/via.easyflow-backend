package via.easyflow.shared.modules.auth.model.user

import java.time.LocalDateTime

data class UserModel(
    val userId: String?,
    val name: String,
    val createdAt: LocalDateTime,
) {
}