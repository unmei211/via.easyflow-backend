package via.easyflow.core.tools.database.query.builder

import via.easyflow.core.tools.database.query.filter.IQueryFilter

interface IQueryBuilder {
    fun build(filters: List<IQueryFilter>): String
    fun merge(filters: List<IQueryFilter>, sql: String): String
    fun merge(filters: String, sql: String): String
}