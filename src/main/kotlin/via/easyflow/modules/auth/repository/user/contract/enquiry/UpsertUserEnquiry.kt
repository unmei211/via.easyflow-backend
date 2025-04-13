package via.easyflow.modules.auth.repository.user.contract.enquiry

import via.easyflow.modules.auth.repository.user.model.UserEntity

data class UpsertUserEnquiry(
    val user: UserEntity
)