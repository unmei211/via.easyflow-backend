package via.easyflow.core.tools.database.query.filter

import via.easyflow.core.tools.database.query.param.IQueryParam

interface IQueryFilter {
    fun filter(): String
    fun params(): List<IQueryParam>
}