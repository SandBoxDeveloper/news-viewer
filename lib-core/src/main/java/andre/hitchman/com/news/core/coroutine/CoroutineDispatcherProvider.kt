package andre.hitchman.com.news.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineDispatcherProvider @Inject constructor() {

    companion object {
        var io: CoroutineDispatcher = Dispatchers.IO
    }
}