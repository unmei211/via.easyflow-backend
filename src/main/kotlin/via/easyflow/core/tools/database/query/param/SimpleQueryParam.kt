package via.easyflow.core.tools.database.query.param

data class SimpleQueryParam(
    private val value: Any,
    private val placeholder: String,
) : IQueryParam {
    override fun getValue(): Any = value

    override fun getPlaceholder(): String = placeholder
}