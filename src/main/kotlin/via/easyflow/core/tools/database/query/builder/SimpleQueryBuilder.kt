package via.easyflow.core.tools.database.query.builder

import org.springframework.stereotype.Component
import via.easyflow.core.tools.database.query.filter.IQueryFilter

@Component
class SimpleQueryBuilder : IQueryBuilder {
    override fun build(filters: List<IQueryFilter>): String {
        val sqlFilter = filters.joinToString(
            separator = " ",
            prefix = "1=1 ",
            postfix = " ",
        ) { it.filter() }
        return sqlFilter
    }

    override fun merge(filters: List<IQueryFilter>, sql: String): String {
        return sql + this.build(filters)
    }

    override fun merge(filters: String, sql: String): String {
        return sql + filters
    }
}