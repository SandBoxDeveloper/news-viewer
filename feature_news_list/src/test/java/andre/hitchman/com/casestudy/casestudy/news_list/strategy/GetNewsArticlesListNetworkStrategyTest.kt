package andre.hitchman.com.casestudy.casestudy.news_list.strategy

import andre.hitchman.com.news.news_list.database.DatabaseMapper
import andre.hitchman.com.casestudy.casestudy.news_list.mocks.CaseStudyResponseMock
import andre.hitchman.com.casestudy.casestudy.news_list.mocks.DatabaseSectionItemsMock
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.core.coroutine.CoroutineDispatcherProvider
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.network.mapper.NewsArticlesListResponseMapper
import andre.hitchman.com.news.network.model.NewsArticlesListResponse
import andre.hitchman.com.news.network.service.NewsArticlesService
import andre.hitchman.com.news.repository.networkcall.SafeNetworkCallDelegate
import andre.hitchman.com.news.testutils.rules.MockKRule
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListNetworkStrategy
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

class GetNewsArticlesListNetworkStrategyTest {

    @get:Rule
    val mockKRule = MockKRule()

    @MockK
    private lateinit var mockNewsArticlesService: NewsArticlesService

    @MockK(relaxed = true)
    private lateinit var mockNewsArticlesListResponseMapper: NewsArticlesListResponseMapper

    @MockK(relaxed = true)
    private lateinit var mockNewsArticleDao: NewsArticleDao

    @MockK(relaxed = true)
    private lateinit var mockCaseStudyMapper: DatabaseMapper

    @MockK
    private lateinit var mockSafeNetworkCallDelegate: SafeNetworkCallDelegate

    @MockK(relaxed = true)
    private lateinit var mockNewsArticlesListResponse: NewsArticlesListResponse

    private lateinit var subject: GetNewsArticlesListNetworkStrategy

    @Before
    fun setUp() {
        subject = GetNewsArticlesListNetworkStrategy(
            mockNewsArticlesService,
            mockNewsArticlesListResponseMapper,
            mockNewsArticleDao,
            mockCaseStudyMapper,
            mockSafeNetworkCallDelegate
        )
    }

    @Test
    fun `getCaseStudies() - when successful then mapper is called`() = runBlocking {
        makeSafeCall {
            mockNewsArticlesService.getNewsArticles()
        }.returns(Response.success(mockNewsArticlesListResponse))

        subject.getNewsArticlesList()

        verify { mockNewsArticlesListResponseMapper.fromResponseMapTo(mockNewsArticlesListResponse) }
    }

    @Test
    fun `requestApi() - verify mapToCaseStudyDatabaseObject is called`() = runBlocking {
        makeSafeCall {
            mockNewsArticlesService.getNewsArticles()
        }.returns(Response.success(mockNewsArticlesListResponse))
        every { mockCaseStudyMapper.mapToNewsArticleDatabaseObject(any()) } returns listOf()

        subject.getNewsArticlesList()

        coVerify { mockCaseStudyMapper.mapToNewsArticleDatabaseObject(listOf()) }
    }

    @Test
    fun `requestApi() - verify mapToSectionItemsDatabaseObject is called`() = runBlocking {
        makeSafeCall {
            mockNewsArticlesService.getNewsArticles()
        }.returns(Response.success(mockNewsArticlesListResponse))
        every { mockCaseStudyMapper.mapToSectionItemsDatabaseObject(any()) } returns listOf()

        subject.getNewsArticlesList()

        coVerify { mockCaseStudyMapper.mapToSectionItemsDatabaseObject(listOf()) }
    }

    @Test
    fun `getCaseStudies() - verify database is updated with new data`() = runBlocking {
        val caseStudies = listOf(
            NewsArticle(
                newsArticleId = CaseStudyResponseMock.id,
                title = CaseStudyResponseMock.titleText,
                teaser = CaseStudyResponseMock.teaserText,
                heroImage = CaseStudyResponseMock.heroImageUrl
            )
        )
        val sectionData = DatabaseSectionItemsMock.generateSectionItemsList()
        makeSafeCall {
            mockNewsArticlesService.getNewsArticles()
        }.returns(Response.success(mockNewsArticlesListResponse))
        every { mockCaseStudyMapper.mapToNewsArticleDatabaseObject(any()) } returns caseStudies
        every { mockCaseStudyMapper.mapToSectionItemsDatabaseObject(any()) } returns sectionData

        subject.getNewsArticlesList()

        coVerify { mockNewsArticleDao.updateDatabaseWithNewData(caseStudies, sectionData) }
    }

    @Test(expected = RemoteNetworkException::class)
    fun `getCaseStudies() - when unsuccessful then exception is thrown`() = runBlocking {
        makeSafeCall {
            mockNewsArticlesService.getNewsArticles()
        }.returns(
            Response.error(
                HttpURLConnection.HTTP_BAD_REQUEST,
                "".toResponseBody(contentType = null)
            )
        )

        subject.getNewsArticlesList()

        return@runBlocking
    }

    private fun <T : Any> CallBlock<T>.returns(responseData: T) {
        coEvery { call() } returns responseData

        coEvery {
            mockSafeNetworkCallDelegate.executeSafeApiCall(
                any<suspend () -> T>(),
                CoroutineDispatcherProvider.io
            )
        } coAnswers { call() }
    }

    private fun <T : Any> makeSafeCall(call: suspend () -> T): CallBlock<T> {
        return CallBlock(call)
    }

    class CallBlock<T : Any>(val call: suspend () -> T)
}