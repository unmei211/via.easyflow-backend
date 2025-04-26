package via.easyflow.interactor.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.manager.IUseCaseManager
import via.easyflow.interactor.usecases.matcher.UseCasesInputMatcher
import kotlin.reflect.KClass

//@Configuration
//class ManagersConfiguration(
//    private val matcher: UseCasesInputMatcher,
//    private val useCaseManagers: List<IUseCaseManager>
//) {
//
//    @Bean
//    @Case(CaseScope.USER)
//    fun userCaseInputMap(
//        @Case(CaseScope.USER) cases: List<TypedUseCase<*, *>>
//    ): Map<KClass<*>, TypedUseCase<*, *>> {
//        val useCasesMap: Map<KClass<*>, TypedUseCase<*, *>> = matcher.match(cases)
//        return useCasesMap
//    }
//}