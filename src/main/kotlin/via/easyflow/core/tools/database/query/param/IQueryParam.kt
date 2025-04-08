package via.easyflow.core.tools.database.query.param

interface IQueryParam {
    fun getValue(): Any
    fun getPlaceholder(): String
}