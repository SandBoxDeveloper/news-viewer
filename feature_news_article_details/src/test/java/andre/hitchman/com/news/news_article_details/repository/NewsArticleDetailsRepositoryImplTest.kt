package andre.hitchman.com.news.news_article_details.repository

import andre.hitchman.com.news.news_article_details.mapper.NewsArticlesSectionDaoMapper
import andre.hitchman.com.news.news_article_details.mocks.CaseStudyDetailsMock
import andre.hitchman.com.news.news_article_details.mocks.CaseStudyResponseMock
import andre.hitchman.com.news.news_article_details.mocks.DatabaseSectionItemsMock
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.testutils.rules.MockKRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class NewsArticleDetailsRepositoryImplTest {

    @get:Rule
    val mockKRule = MockKRule()

    @MockK(relaxed = true)
    private lateinit var mockNewsArticlesDatabase: NewsArticleDao

    @MockK(relaxed = true)
    private lateinit var mockNewsArticlesSectionDaoMapper: NewsArticlesSectionDaoMapper

    private val subject by lazy {
        NewsArticleDetailsRepositoryImpl(
            mockNewsArticlesDatabase,
            mockNewsArticlesSectionDaoMapper
        )
    }

    @Test
    fun `getNewsArticlesDetails(with id) - is successful and result returns section items and title then NewsArticleDetails is returned`() =
        runBlocking {
            val sectionItemsResponse = DatabaseSectionItemsMock.generateSectionItemsList()
            val expected = CaseStudyDetailsMock.generateCaseStudyDetails()

            coEvery {
                mockNewsArticlesDatabase.getSectionItems(1)
            } returns sectionItemsResponse
            coEvery {
                mockNewsArticlesDatabase.getNewsArticleTitle(1)
            } returns CaseStudyResponseMock.titleText
            every {
                mockNewsArticlesSectionDaoMapper.mapToNewsArticleDetails(
                    1,
                    CaseStudyResponseMock.titleText,
                    sectionItemsResponse
                )
            } returns CaseStudyDetailsMock.generateCaseStudyDetails()

            val result = subject.getNewsArticleDetails(1)

            assertThat(result).isEqualTo(expected)
        }

    @Test(expected = RemoteNetworkException::class)
    fun `getNewsArticlesDetails(with id) - when getting section items fails then RemoteNetworkException is thrown`() =
        runBlocking {
            coEvery { mockNewsArticlesDatabase.getSectionItems(1) } throws Exception()

            subject.getNewsArticleDetails(1)

            return@runBlocking
        }

    @Test(expected = RemoteNetworkException::class)
    fun `getNewsArticlesDetails(with id) - when getting title fails then RemoteNetworkException is thrown`() =
        runBlocking {
            coEvery { mockNewsArticlesDatabase.getNewsArticleTitle(1) } throws Exception()

            subject.getNewsArticleDetails(1)

            return@runBlocking
        }
}