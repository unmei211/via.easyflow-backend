package via.easyflow.modules.task.internal.service.filter.user_task.resolver

import org.springframework.stereotype.Component
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.filter.NativeQueryFilter
import via.easyflow.core.tools.database.query.resolver.IQueryFiltersResolver
import via.easyflow.modules.task.internal.service.filter.user_task.model.UserTaskFilterModel

@Component
final class UserTaskFiltersResolver : IQueryFiltersResolver<UserTaskFilterModel> {
    private val chainOfResponsibility = listOf(
        this::resolveUserIdFilter,
        this::resolveTaskIdFilter,
        this::resolveProjectIdFilter
    )

    private fun resolveUserIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.userId?.let {
            NativeQueryFilter(
                "AND userId = :userId",
                mapOf("userId" to it)
            )
        }

    private fun resolveTaskIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.taskId?.let {
            NativeQueryFilter(
                "AND taskId = :taskId",
                mapOf("taskId" to it)
            )
        }

    private fun resolveProjectIdFilter(model: UserTaskFilterModel): IQueryFilter? =
        model.taskId?.let {
            NativeQueryFilter(
                "AND taskId = :taskId",
                mapOf("taskId" to it)
            )
        }

    override fun resolve(model: UserTaskFilterModel): List<IQueryFilter> {
        return chainOfResponsibility.mapNotNull { resolver ->
            resolver(model)
        }
    }
}