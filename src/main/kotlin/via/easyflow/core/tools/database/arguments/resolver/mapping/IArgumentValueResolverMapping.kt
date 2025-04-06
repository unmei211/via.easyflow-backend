package via.easyflow.core.tools.database.arguments.resolver.mapping

import via.easyflow.core.tools.database.arguments.resolver.IArgumentValueResolver

interface IArgumentValueResolverMapping {
    fun get(placeholder: String): IArgumentValueResolver
}