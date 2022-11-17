package kg.sl.rate.core.model

sealed interface ResourceResult<out T> {
    data class Success<T>(val data: T) : ResourceResult<T>
    data class Error(val exception: Throwable? = null) : ResourceResult<Nothing>
    object Loading : ResourceResult<Nothing>
}
