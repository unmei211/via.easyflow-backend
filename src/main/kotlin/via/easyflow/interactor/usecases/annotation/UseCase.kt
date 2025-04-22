package via.easyflow.interactor.usecases.annotation

import org.springframework.beans.factory.annotation.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Qualifier
annotation class UseCase(
    val scope: String
)
