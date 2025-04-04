package via.easyflow.modules.auth.internal.repository.user.contract.enquiry

import via.easyflow.modules.auth.internal.entity.UserEntity

data class UpsertUserEnquiry(
    val user: UserEntity
)