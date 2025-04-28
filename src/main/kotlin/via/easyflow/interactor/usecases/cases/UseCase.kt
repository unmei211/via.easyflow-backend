package via.easyflow.interactor.usecases.cases

interface UseCase<T, R> {
    fun invoke(input: T): R
}