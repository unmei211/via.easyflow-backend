package via.easyflow.core.tools.database.query.condition

import org.springframework.stereotype.Component
import via.easyflow.core.tools.database.query.param.IQueryParam

@Component("jsonb-condition-wrapper")
class JsonbConditionWrapper(
) : IConditionWrapper {
    override fun wrap(fieldName: String, condition: String): String {
        return "$condition document ->> '$fieldName' = :$fieldName"
    }

    override fun wrap(fieldName: String, placeholder: String, condition: String): String {
        return "$condition document ->> '$fieldName' = :$placeholder"
    }

    override fun wrap(vararg bundles: Pair<Pair<String, String>, String>): String {
        return bundles.joinToString(" ") { wrap(it.first.first, it.first.second, it.second) }
    }

    override fun wrap(param: List<IQueryParam>, condition: String): String {
        val arr: List<Pair<Pair<String, String>, String>> =
            param.map { it.getPlaceholder() to it.getPlaceholder() to condition }
        return this.wrap(*arr.toTypedArray())
    }
}