package via.easyflow.interactor.configuration.factory

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import via.easyflow.interactor.usecases.TypedUseCase
import kotlin.reflect.KClass

@Component("useCasesInputFactory")
class UseCasesInputsFactory(
    private val beanFactory: ConfigurableListableBeanFactory
) {

    fun createMapBeanDefinition(useCasesBeans: List<String>): Map<KClass<*>, TypedUseCase<*, *>> {
        val associate = useCasesBeans.associate {
            val useCaseBean: TypedUseCase<*, *> = beanFactory.getBean(it) as TypedUseCase<*, *>

            val inputType: KClass<*> = useCaseBean.getInputType()

            inputType to useCaseBean
        }
        return associate
    }
}