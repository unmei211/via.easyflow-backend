package via.easyflow.modules.auth.internal.repository.user

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.auth.internal.entity.UserEntity
import via.easyflow.modules.auth.internal.repository.user.contract.enquiry.UpsertUserEnquiry
import via.easyflow.modules.auth.internal.repository.user.contract.query.FindAllUsersQuery
import via.easyflow.modules.auth.internal.repository.user.contract.query.FindByIdUserQuery

interface IUserRepository {
    fun upsert(enquiry: UpsertUserEnquiry): Mono<UserEntity>
    fun findAll(query: FindAllUsersQuery?): Flux<UserEntity>
    fun findById(query: FindByIdUserQuery): Mono<UserEntity>
    fun existsById(query: FindByIdUserQuery): Mono<Boolean>
}