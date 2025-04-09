package via.easyflow.core.tools.database.query.resolver

import via.easyflow.core.tools.database.query.filter.IQueryFilter

interface IQueryFiltersResolver<T> {
    fun resolve(model: T): List<IQueryFilter>
}