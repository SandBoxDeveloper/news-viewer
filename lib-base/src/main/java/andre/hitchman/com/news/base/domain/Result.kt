package andre.hitchman.com.news.base.domain

sealed class Result<out T : Any> {

    data class Success<out T : Any>(
        val data: T
    ) : Result<T>()

    object Error : Result<Nothing>()
}