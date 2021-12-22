package andre.hitchman.com.casestudy.casestudy.news_list.repository

import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListNetworkStrategy
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListOfflineStrategy
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.testutils.rules.MockKRule
import andre.hitchman.com.news.news_list.repository.NewsArticlesListRepositoryImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CaseStudiesListRepositoryImplTest {

    @get:Rule
    val mockKRule = MockKRule()

    @MockK
    private lateinit var mockGetNewsArticlesListNetworkStrategy: GetNewsArticlesListNetworkStrategy

    @MockK
    private lateinit var mockGetNewsArticlesListOfflineStrategy: GetNewsArticlesListOfflineStrategy

    @MockK(relaxed = true)
    private lateinit var mockNewsArticleItem: NewsArticleItem

    private val subject by lazy { NewsArticlesListRepositoryImpl() }

    @Test
    fun `getCaseStudies() - when network strategy is executed successfully then list of CaseStudyItem is returned`() =
        runBlocking {
            coEvery {
                mockGetNewsArticlesListNetworkStrategy.getNewsArticlesList()
            } returns listOf(
                mockNewsArticleItem
            )

            val result = subject.getNewsArticles(
                newsArticleListStrategy = listOf(mockGetNewsArticlesListNetworkStrategy)
            )

            assertThat(result).isEqualTo(listOf(mockNewsArticleItem))
        }

    @Test
    fun `getCaseStudies() - when offline strategy is executed successfully then list of CaseStudyItem is returned`() =
        runBlocking {
            coEvery {
                mockGetNewsArticlesListOfflineStrategy.getNewsArticlesList()
            } returns listOf(
                mockNewsArticleItem
            )

            val result = subject.getNewsArticles(
                newsArticleListStrategy = listOf(mockGetNewsArticlesListOfflineStrategy)
            )

            assertThat(result).isEqualTo(listOf(mockNewsArticleItem))
        }

    @Test(expected = RemoteNetworkException::class)
    fun `getCaseStudies() - when network strategy fails then RemoteNetworkException is thrown`() =
        runBlocking {
            coEvery {
                mockGetNewsArticlesListNetworkStrategy.getNewsArticlesList()
            } throws RemoteNetworkException()

            subject.getNewsArticles(
                newsArticleListStrategy = listOf(
                    mockGetNewsArticlesListNetworkStrategy
                )
            )

            return@runBlocking
        }

    @Test(expected = RemoteNetworkException::class)
    fun `getCaseStudies() - when offline strategy fails then RemoteNetworkException is thrown`() =
        runBlocking {
            coEvery {
                mockGetNewsArticlesListOfflineStrategy.getNewsArticlesList()
            } throws RemoteNetworkException()

            subject.getNewsArticles(
                newsArticleListStrategy = listOf(
                    mockGetNewsArticlesListOfflineStrategy
                )
            )

            return@runBlocking
        }
}