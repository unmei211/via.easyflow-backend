package via.easyflow.core.tools.database.arguments.resolver.mapping

import via.easyflow.core.tools.database.arguments.resolver.IArgumentValueResolver

class ArgumentValueResolverMapping(
    private val resolversMapping: Map<String, IArgumentValueResolver>,
    private val default: IArgumentValueResolver
) : IArgumentValueResolverMapping {
    override fun get(placeholder: String): IArgumentValueResolver {
        return resolversMapping.getOrDefault(placeholder, default)
    }
}