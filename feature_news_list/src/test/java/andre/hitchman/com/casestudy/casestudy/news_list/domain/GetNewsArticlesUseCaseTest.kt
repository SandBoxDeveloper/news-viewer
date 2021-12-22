package andre.hitchman.com.casestudy.casestudy.news_list.domain

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_list.repository.NewsArticlesRepository
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListNetworkStrategy
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListOfflineStrategy
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.network.NetworkChecker
import andre.hitchman.com.news.testutils.rules.MockKRule
import andre.hitchman.com.news.news_list.domain.GetNewsArticlesUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetNewsArticlesUseCaseTest {

    private companion object {
        val EXPECTED_RESULT = listOf<NewsArticleItem>()
    }

    @get:Rule
    val mockKRule = MockKRule()

    @RelaxedMockK
    private lateinit var mockNewsArticlesRepository: NewsArticlesRepository

    @RelaxedMockK
    private lateinit var mockGetNewsArticlesListNetworkStrategy: GetNewsArticlesListNetworkStrategy

    @RelaxedMockK
    private lateinit var mockGetNewsArticlesListOfflineStrategy: GetNewsArticlesListOfflineStrategy

    @RelaxedMockK
    private lateinit var mockNetworkChecker: NetworkChecker

    private lateinit var subject: GetNewsArticlesUseCase

    @Before
    fun setUp() {
        subject =
            GetNewsArticlesUseCase(
                mockNewsArticlesRepository,
                mockGetNewsArticlesListNetworkStrategy,
                mockGetNewsArticlesListOfflineStrategy,
                mockNetworkChecker
            )
    }

    @Test
    fun `execute() - success and network is available, then ResultSuccess returned with list of case studies`() =
        runBlocking {
            every { mockNetworkChecker.isNetworkAvailable() } returns true
            coEvery {
                mockNewsArticlesRepository.getNewsArticles(
                    newsArticleListStrategy = listOf(mockGetNewsArticlesListNetworkStrategy)
                )
            } returns emptyList()

            val result = subject.execute()

            coVerify {
                mockNewsArticlesRepository.getNewsArticles(
                    listOf(
                        mockGetNewsArticlesListNetworkStrategy
                    )
                )
            }
            assertThat(result is Result.Success).isTrue()
            assertThat((result as Result.Success).data).isEqualTo(EXPECTED_RESULT)
            return@runBlocking
        }

    @Test
    fun `execute() - success and network is not available, then ResultSuccess returned with list of case studies`() =
        runBlocking {
            every { mockNetworkChecker.isNetworkAvailable() } returns false
            coEvery {
                mockNewsArticlesRepository.getNewsArticles(
                    newsArticleListStrategy = listOf(mockGetNewsArticlesListOfflineStrategy)
                )
            } returns emptyList()

            val result = subject.execute()

            coVerify {
                mockNewsArticlesRepository.getNewsArticles(listOf(mockGetNewsArticlesListOfflineStrategy))
            }
            assertThat(result is Result.Success).isTrue()
            assertThat((result as Result.Success).data).isEqualTo(EXPECTED_RESULT)
            return@runBlocking
        }

    @Test
    fun `execute() - exception, then ResultError returned`() =
        runBlocking {
            every { mockNetworkChecker.isNetworkAvailable() } returns true

            coEvery {
                mockNewsArticlesRepository.getNewsArticles(
                    listOf(mockGetNewsArticlesListNetworkStrategy)
                )
            } throws RemoteNetworkException()

            val result = subject.execute()

            assertThat(result is Result.Error).isTrue()
        }

    @Test(expected = CancellationException::class)
    fun `execute() - cancellation exception, then ResultError returned`() =
        runBlocking {
            every { mockNetworkChecker.isNetworkAvailable() } returns true

            coEvery {
                mockNewsArticlesRepository.getNewsArticles(
                    listOf(
                        mockGetNewsArticlesListNetworkStrategy
                    )
                )
            } throws CancellationException()

            subject.execute()

            return@runBlocking
        }
}