package via.easyflow.core.tools.version.comparator

interface IVersionComparator<T> {
    fun compare(first: String, second: String)
    fun compare(first: T, second: T)
}