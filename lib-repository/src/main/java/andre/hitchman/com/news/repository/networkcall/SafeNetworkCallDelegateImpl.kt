package andre.hitchman.com.news.repository.networkcall

import andre.hitchman.com.news.core.RemoteNetworkException
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SafeNetworkCallDelegateImpl @Inject constructor() : SafeNetworkCallDelegate {

    override suspend fun <T : Any> executeSafeApiCall(
        networkRequest: suspend () -> T,
        coroutineContext: CoroutineContext
    ): T {
        return withContext(coroutineContext) {
            try {
                networkRequest.invoke()
            } catch (throwable: Throwable) {
                if (throwable is CancellationException) {
                    throw throwable
                }
                throw RemoteNetworkException()
            }
        }
    }
}
