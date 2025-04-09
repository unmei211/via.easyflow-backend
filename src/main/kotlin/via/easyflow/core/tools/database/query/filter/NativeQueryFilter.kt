package via.easyflow.core.tools.database.query.filter

import via.easyflow.core.tools.database.query.param.IQueryParam
import via.easyflow.core.tools.database.query.param.SimpleQueryParam

class NativeQueryFilter(
    private val sqlFilter: String,
    private val bindMap: Map<String, Any>,
    private val listOfParams: List<IQueryParam> = bindMap.entries.map { (k, v) ->
        SimpleQueryParam(v, k)
    }
) : IQueryFilter {
    override fun filter(): String {
        return sqlFilter
    }

    override fun params(): List<IQueryParam> {
        return listOfParams
    }
}