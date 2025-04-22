package via.easyflow.interactor.configuration

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.Configuration
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.UseCase
import kotlin.reflect.KClass

@Configuration
class UseCaseAutoConfiguration : BeanFactoryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beans = beanFactory.getBeansWithAnnotation(UseCase::class.java)

        val scopedBeans = beans.values.groupBy {
            it.javaClass.getAnnotation(UseCase::class.java).scope
        }

        scopedBeans.forEach { (scope, useCases) ->
            val mapBeanName = "${scope.lowercase()}ScopedInputs"
            val mapBeanDefinition = createMapBeanDefinition(useCases)

            beanFactory.registerSingleton(mapBeanName, mapBeanDefinition)
        }
    }

    private fun createMapBeanDefinition(useCases: List<Any>): Map<KClass<*>, TypedUseCase<*, *>> {
        return useCases.associateBy(
            keySelector = { it::class },
            valueTransform = { it as TypedUseCase<*, *> }
        )
    }
}