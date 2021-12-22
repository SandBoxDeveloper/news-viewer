package andre.hitchman.com.news.news_article_details.viewmodel

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_article_details.domain.GetNewsArticleDetailsUseCase
import andre.hitchman.com.news.news_article_details.livedatastate.NewsArticleDetailsRequestState
import andre.hitchman.com.news.news_article_details.mocks.CaseStudyDetailsMock
import andre.hitchman.com.news.testutils.rules.CoroutineRule
import andre.hitchman.com.news.testutils.rules.MockKRule
import andre.hitchman.com.news.testutils.utils.getOrAwaitValue
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsArticlesDetailsViewModelTest {

    @get:Rule
    val mockKRule = MockKRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = CoroutineRule()

    @MockK(relaxed = true)
    private lateinit var mockGetNewsArticleDetailsUseCase: GetNewsArticleDetailsUseCase

    private lateinit var subject: NewsArticlesDetailsViewModel

    @Before
    fun setUp() {
        subject = NewsArticlesDetailsViewModel(mockGetNewsArticleDetailsUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loadSelectedCaseStudyDetail() - request executed then state is Loading`() =
        runBlocking {
            coroutineRule.pauseDispatcher()
            coEvery { mockGetNewsArticleDetailsUseCase.execute(1) } returns Result.Success(
                CaseStudyDetailsMock.generateCaseStudyDetails()
            )

            subject.loadSelectedNewsArticleDetail(1)

            assertThat(subject.getNewsArticleDetailsRequestState.getOrAwaitValue()).isEqualTo(
                NewsArticleDetailsRequestState.Loading
            )
            coroutineRule.resumeDispatcher()
        }

    @Test
    fun `loadSelectedCaseStudyDetail() - GIVEN list of items, WHEN request executed, THEN state is StateData returning list of SectionItems`() =
        runBlocking {
            val expectedList = CaseStudyDetailsMock.generateCaseStudyDetails()
            coEvery {
                mockGetNewsArticleDetailsUseCase.execute(1)
            } returns Result.Success(CaseStudyDetailsMock.generateCaseStudyDetails())

            subject.loadSelectedNewsArticleDetail(1)

            assertThat((subject.getNewsArticleDetailsRequestState.getOrAwaitValue() as NewsArticleDetailsRequestState.Data).newsArticleDetails)
                .isEqualTo(expectedList)
        }

    @Test
    fun `loadSelectedCaseStudyDetail() - given Result is Error then state returned is StateError`() =
        runBlocking {
            coEvery { mockGetNewsArticleDetailsUseCase.execute(1) } returns Result.Error

            subject.loadSelectedNewsArticleDetail(1)

            assertThat((subject.getNewsArticleDetailsRequestState.getOrAwaitValue())).isEqualTo(
                NewsArticleDetailsRequestState.Error
            )
        }

    @Test
    fun `loadSelectedCaseStudyDetail() - WHEN result is Error, THEN state is Error`() =
        runBlocking {
            coEvery {
                mockGetNewsArticleDetailsUseCase.execute(any())
            } returns Result.Error

            subject.loadSelectedNewsArticleDetail(1)

            assertThat((subject.getNewsArticleDetailsRequestState.getOrAwaitValue())).isEqualTo(
                NewsArticleDetailsRequestState.Error
            )
        }
}