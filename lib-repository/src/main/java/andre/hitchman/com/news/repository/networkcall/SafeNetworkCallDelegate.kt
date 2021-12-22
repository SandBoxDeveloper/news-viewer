package andre.hitchman.com.news.repository.networkcall

import kotlin.coroutines.CoroutineContext

interface SafeNetworkCallDelegate {

    suspend fun <T : Any> executeSafeApiCall(
        networkRequest: suspend () -> T,
        coroutineContext: CoroutineContext
    ): T
}