package via.easyflow.core.tools.database.query.param.filler

import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.param.IQueryParam

interface IQueryParamMapper<T, R> {
    fun fill(filters: List<IQueryFilter>, clientBinder: T): R
    fun fill(params: List<IQueryParam>, clientBinder: T): R
}