package via.easyflow.interactor.usecases

interface UseCase<T, R> {
    fun invoke(input: T): R
}