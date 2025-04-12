package via.easyflow.core.tools.database.query.condition

import via.easyflow.core.tools.database.query.param.IQueryParam

interface IConditionWrapper {
    fun wrap(fieldName: String, placeholder: String, condition: String): String
    fun wrap(fieldName: String, condition: String): String
    fun wrap(vararg bundles: Pair<Pair<String, String>, String>): String
    fun wrap(param: List<IQueryParam>, condition: String): String
}