package via.easyflow.shared.modules.auth.api.outputs.user

import via.easyflow.shared.modules.auth.model.user.UserModel

data class UpsertUserOut(
    val user: UserModel
) {
}