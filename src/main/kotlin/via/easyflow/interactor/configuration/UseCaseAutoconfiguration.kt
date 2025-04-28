package via.easyflow.interactor.configuration

import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.annotation.Configuration
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case

@Configuration
class UseCaseAutoConfiguration : BeanDefinitionRegistryPostProcessor {


    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
        val beanFactory = registry as? DefaultListableBeanFactory
            ?: throw IllegalArgumentException("Bean factory must be a DefaultListableBeanFactory")

        val useCaseBeanDefinitions = beanFactory.getBeanNamesForType(TypedUseCase::class.java)

        val scopeGroups = mutableMapOf<String, MutableList<String>>()

        useCaseBeanDefinitions.forEach { beanName ->
            val beanDefinition = beanFactory.getBeanDefinition(beanName)
            val className = beanDefinition.beanClassName

            val clazz = Class.forName(className)

            if (TypedUseCase::class.java.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Case::class.java)) {
                val case = clazz.getAnnotation(Case::class.java)
                scopeGroups.computeIfAbsent(case.scope) { mutableListOf() }.add(beanName)
            }
        }

        scopeGroups.forEach { (scopeName, groups) ->
            val scopedUseCases = "$scopeName-UseCaseInputType"

            if (!registry.containsBeanDefinition(scopedUseCases)) {
                val qualifier = AutowireCandidateQualifier(Case::class.java)
                qualifier.setAttribute("scope", scopeName)

                val beanDef = GenericBeanDefinition().apply {
                    setBeanClass(Map::class.java)
                    factoryBeanName = "useCasesInputFactory"
                    factoryMethodName = "createMapBeanDefinition"
                    constructorArgumentValues.addGenericArgumentValue(groups)
                    addQualifier(qualifier)
                }
                registry.registerBeanDefinition(scopedUseCases, beanDef)
            }
        }
    }
}