package andre.hitchman.com.news.repository.networkcall

import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.core.coroutine.BaseCoroutineTest
import andre.hitchman.com.news.core.coroutine.CoroutineDispatcherProvider
import andre.hitchman.com.news.testutils.rules.MockKRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class SafeNetworkCallDelegateImplTest : BaseCoroutineTest() {

    private interface MockNetworkCallDelegateInterface {
        suspend fun apiCall()
    }

    @get:Rule
    val mockKRule = MockKRule()

    @MockK(relaxed = true)
    private lateinit var mockNetworkCallDelegate: MockNetworkCallDelegateInterface

    private val subject by lazy {
        SafeNetworkCallDelegateImpl()
    }

    @Test
    fun `executeSafeApiCall() - Api call runs successfully, then api call is executed`() =
        runBlocking {
            coEvery { mockNetworkCallDelegate.apiCall() } just Runs

            subject.executeSafeApiCall(
                { mockNetworkCallDelegate.apiCall() },
                CoroutineDispatcherProvider.io
            )

            coVerify { mockNetworkCallDelegate.apiCall() }
        }

    @Test(expected = RemoteNetworkException::class)
    fun `executeSafeApiCall() - when unsuccessful with IOException, then exception is thrown`() =
        runBlocking {
            coEvery { mockNetworkCallDelegate.apiCall() } throws IOException()

            subject.executeSafeApiCall(
                { mockNetworkCallDelegate.apiCall() },
                CoroutineDispatcherProvider.io
            )
        }


    @Test(expected = RemoteNetworkException::class)
    fun `executeSafeApiCall() - when unsuccessful with HTTP Internal Error, then exception is thrown`() =
        runBlocking {
            coEvery { mockNetworkCallDelegate.apiCall() } throws mockk<HttpException> {
                every { code() } returns HttpURLConnection.HTTP_INTERNAL_ERROR
            }

            subject.executeSafeApiCall(
                { mockNetworkCallDelegate.apiCall() },
                CoroutineDispatcherProvider.io
            )
        }

    @Test(expected = CancellationException::class)
    fun `executeSafeApiCall() - when unsuccessful with CancellationException, then CancellationException is thrown`() =
        runBlocking {
            coEvery { mockNetworkCallDelegate.apiCall() } throws CancellationException()

            subject.executeSafeApiCall(
                { mockNetworkCallDelegate.apiCall() },
                CoroutineDispatcherProvider.io
            )
        }
}