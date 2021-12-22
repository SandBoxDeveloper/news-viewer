package andre.hitchman.com.casestudy.casestudy.news_list.strategy

import andre.hitchman.com.news.news_list.mapper.NewsArticleDaoMapper
import andre.hitchman.com.casestudy.casestudy.news_list.mocks.CaseStudyResponseMock
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.testutils.mocks.NewsArticleItemMock
import andre.hitchman.com.news.testutils.rules.MockKRule
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListOfflineStrategy
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetNewsArticlesListOfflineStrategyTest {

    @get:Rule
    val mockKRule = MockKRule()

    @MockK(relaxed = true)
    private lateinit var mockCaseStudiesDatabase: NewsArticleDao

    @MockK(relaxed = true)
    private lateinit var mockNewsArticleDaoMapper: NewsArticleDaoMapper

    private lateinit var subject: GetNewsArticlesListOfflineStrategy

    @Before
    fun setUp() {
        subject = GetNewsArticlesListOfflineStrategy(mockCaseStudiesDatabase, mockNewsArticleDaoMapper)
    }

    @Test
    fun `getCaseStudiesList() - is successful and result returns an empty list then an empty list of CaseStudyItem is returned`() =
        runBlocking {
            coEvery { mockCaseStudiesDatabase.getAllNewsArticles() } returns emptyList()

            val result = subject.getNewsArticlesList()

            assertThat(result).isEmpty()
        }

    @Test
    fun `getCaseStudiesList() - is successful and result returns null then an empty list of CaseStudyItem is returned`() =
        runBlocking {
            coEvery { mockCaseStudiesDatabase.getAllNewsArticles() } returns null

            val result = subject.getNewsArticlesList()

            assertThat(result).isEmpty()
        }

    @Test
    fun `getCaseStudiesList() - is successful and result returns an list then a list of CaseStudyItem is returned`() =
        runBlocking {
            val response = listOf(
                NewsArticle(
                    newsArticleId = CaseStudyResponseMock.id,
                    title = CaseStudyResponseMock.titleText,
                    teaser = CaseStudyResponseMock.teaserText,
                    heroImage = CaseStudyResponseMock.heroImageUrl
                )
            )
            val expected = NewsArticleItemMock.generateNewsArticleItemList()

            coEvery {
                mockCaseStudiesDatabase.getAllNewsArticles()
            } returns response
            every {
                mockNewsArticleDaoMapper.fromResponseTo(response)
            } returns expected

            val result = subject.getNewsArticlesList()

            assertThat(result).isEqualTo(expected)
        }

    @Test(expected = RemoteNetworkException::class)
    fun `getCaseStudiesList() - fails then RemoteNetworkException is thrown`() = runBlocking {
        coEvery { mockCaseStudiesDatabase.getAllNewsArticles() } throws Exception()

        subject.getNewsArticlesList()

        return@runBlocking
    }
}