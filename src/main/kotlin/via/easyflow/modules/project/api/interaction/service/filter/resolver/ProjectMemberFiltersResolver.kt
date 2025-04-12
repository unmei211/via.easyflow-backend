package via.easyflow.modules.project.api.interaction.service.filter.resolver

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import via.easyflow.core.tools.database.query.condition.IConditionWrapper
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.filter.embedded.UserIdQueryFilter
import via.easyflow.core.tools.database.query.resolver.IQueryFiltersResolver
import via.easyflow.modules.project.api.interaction.service.filter.model.ProjectMemberFilterModel

@Component
final class ProjectMemberFiltersResolver(
    @Qualifier("jsonb-condition-wrapper") private val jsonbWrapper: IConditionWrapper,
) : IQueryFiltersResolver<ProjectMemberFilterModel> {
    private val chainOfResponsibility = listOf(
        this::resolveUserIdFilter,
        this::resolveProjectIdFilter,
    )

    private fun resolveProjectIdFilter(model: ProjectMemberFilterModel): IQueryFilter? =
        model.projectId?.let {
            UserIdQueryFilter(model.projectId, jsonbWrapper)
        }

    private fun resolveUserIdFilter(model: ProjectMemberFilterModel): IQueryFilter? =
        model.userId?.let {
            UserIdQueryFilter(model.userId, jsonbWrapper)
        }

    override fun resolve(model: ProjectMemberFilterModel): List<IQueryFilter> {
        return chainOfResponsibility.mapNotNull { resolver ->
            resolver(model)
        }
    }
}