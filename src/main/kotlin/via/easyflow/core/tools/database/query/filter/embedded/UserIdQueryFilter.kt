package via.easyflow.core.tools.database.query.filter.embedded

import via.easyflow.core.tools.database.query.condition.IConditionWrapper
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.param.IQueryParam
import via.easyflow.core.tools.database.query.param.SimpleQueryParam

class UserIdQueryFilter(
    private val userId: String,
    private val wrapper: IConditionWrapper,
    private val condition: String = "AND"
) : IQueryFilter {
    private val params = mutableListOf<IQueryParam>(SimpleQueryParam(userId, "userId"))
    private val filter = wrapper.wrap(params, condition)

    override fun filter(): String {
        return filter
    }

    override fun params(): List<IQueryParam> {
        return params
    }
}