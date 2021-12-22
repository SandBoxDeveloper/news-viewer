package andre.hitchman.com.news.core.coroutine

import kotlinx.coroutines.Dispatchers
import org.junit.BeforeClass

abstract class BaseCoroutineTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupBeforeClassTestCoroutineDispatcherProvider() {
            with(CoroutineDispatcherProvider) {
                io = Dispatchers.Unconfined
            }
        }
    }
}