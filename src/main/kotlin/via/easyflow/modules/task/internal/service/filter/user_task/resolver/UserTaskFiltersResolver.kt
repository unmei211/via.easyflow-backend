package via.easyflow.modules.task.internal.service.filter.user_task.resolver

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import via.easyflow.core.tools.database.query.condition.IConditionWrapper
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.filter.NativeQueryFilter
import via.easyflow.core.tools.database.query.resolver.IQueryFiltersResolver
import via.easyflow.modules.task.internal.service.filter.user_task.model.UserTaskFilterModel

@Component
final class UserTaskFiltersResolver(
    @Qualifier("jsonb-condition-wrapper") private val jsonbWrapper: IConditionWrapper,
    @Qualifier("relation-condition-wrapper") private val relationWrapper: IConditionWrapper,
) : IQueryFiltersResolver<UserTaskFilterModel> {
    private val chainOfResponsibility = listOf(
        this::resolveUserIdFilter,
        this::resolveTaskIdFilter,
        this::resolveProjectIdFilter,
        this::resolveOwnerUserIdFilter
    )

    private fun resolveUserIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.userId?.let {
            NativeQueryFilter(
                jsonbWrapper.wrap("userId" to "userId" to "AND"),
                mapOf("userId" to it)
            )
        }

    private fun resolveTaskIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.taskId?.let {
            NativeQueryFilter(
                jsonbWrapper.wrap("taskId", "AND"),
                mapOf("taskId" to it)
            )
        }

    private fun resolveProjectIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.projectId?.let {
            NativeQueryFilter(
                jsonbWrapper.wrap("projectId", "AND"),
                mapOf("projectId" to it)
            )
        }

    private fun resolveOwnerUserIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.ownerUserId?.let {
            NativeQueryFilter(
                jsonbWrapper.wrap("ownerUserId", "AND"),
                mapOf("ownerUserId" to it)
            )
        }

    override fun resolve(model: UserTaskFilterModel): List<IQueryFilter> {
        return chainOfResponsibility.mapNotNull { resolver ->
            resolver(model)
        }
    }
}