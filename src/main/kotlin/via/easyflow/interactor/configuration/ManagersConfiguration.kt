package via.easyflow.interactor.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.interactor.usecases.annotation.UseCase
import via.easyflow.interactor.usecases.manager.IUseCaseManager
import via.easyflow.interactor.usecases.matcher.UseCasesInputMatcher
import kotlin.reflect.KClass

@Configuration
class ManagersConfiguration(
    private val matcher: UseCasesInputMatcher,
    private val useCaseManagers: List<IUseCaseManager>
) {

    @Bean
    @UseCase(CaseScope.USER)
    fun userCaseInputMap(
        @UseCase(CaseScope.USER) useCases: List<TypedUseCase<*, *>>
    ): Map<KClass<*>, TypedUseCase<*, *>> {
        val useCasesMap: Map<KClass<*>, TypedUseCase<*, *>> = matcher.match(useCases)
        return useCasesMap
    }
}