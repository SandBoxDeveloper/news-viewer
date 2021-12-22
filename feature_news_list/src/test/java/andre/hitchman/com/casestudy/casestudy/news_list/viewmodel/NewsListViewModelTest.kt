package andre.hitchman.com.casestudy.casestudy.news_list.viewmodel

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_list.database.DatabaseMapper
import andre.hitchman.com.news.news_list.domain.GetNewsArticlesUseCase
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListEventState
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListNavigationState
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListRequestState
import andre.hitchman.com.news.news_list.model.UICaseStudyList
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.network.NetworkChecker
import andre.hitchman.com.news.testutils.rules.CoroutineRule
import andre.hitchman.com.news.testutils.rules.MockKRule
import andre.hitchman.com.news.testutils.utils.getOrAwaitValue
import andre.hitchman.com.news.news_list.viewmodel.NewsListViewModel
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class NewsListViewModelTest {

    @get:Rule
    val mockKRule = MockKRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = CoroutineRule()

    @MockK
    private lateinit var mockGetNewsArticlesUseCase: GetNewsArticlesUseCase

    @MockK(relaxed = true)
    private lateinit var mockNewsArticleDao: NewsArticleDao

    @MockK(relaxed = true)
    private lateinit var mockCaseStudyMapper: DatabaseMapper

    @MockK(relaxed = true)
    private lateinit var mockNetworkChecker: NetworkChecker

    companion object {
        val item = NewsArticleItem.NewsArticleItemBuilder()
            .newsArticleId(1)
            .teaserText("")
            .newsArticleTitle("")
            .heroImage("")
            .sectionsList(listOf())
            .build()
    }

    private lateinit var subject: NewsListViewModel

    @Before
    fun setUp() {
        coEvery { mockGetNewsArticlesUseCase.execute() } returns Result.Success(
            listOf(item)
        )

        subject = NewsListViewModel(
            mockGetNewsArticlesUseCase,
            mockNetworkChecker
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when subject is instantiated - then state is loading`() = runBlocking {
        coroutineRule.pauseDispatcher()

        subject = NewsListViewModel(
            mockGetNewsArticlesUseCase,
            mockNetworkChecker
        )

        assertThat(subject.getNewsArticlesStateNewsArticlesList.getOrAwaitValue()).isEqualTo(
            NewsArticlesListRequestState.Loading
        )
        coroutineRule.resumeDispatcher()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `requestApi() - request executed then state is Loading`() = runBlocking {
        coroutineRule.pauseDispatcher()

        subject.requestApi()

        assertThat(subject.getNewsArticlesStateNewsArticlesList.getOrAwaitValue()).isEqualTo(
            NewsArticlesListRequestState.Loading
        )
        coroutineRule.resumeDispatcher()
    }

    @Test
    fun `requestApi() - GIVEN list of item, WHEN request executed, THEN state is Completed with CaseStudyUIDataWithData returning list of case study items`() =
        runBlocking {
            coEvery { mockGetNewsArticlesUseCase.execute() } returns Result.Success(
                listOf(item)
            )

            subject.requestApi()

            assertThat((subject.getNewsArticlesStateNewsArticlesList.getOrAwaitValue() as NewsArticlesListRequestState.Completed).caseStudy)
                .isEqualTo(UICaseStudyList.WithCaseStudyList(listOf(item)))
        }

    @Test
    fun `requestApi() - GIVEN empty list of items, WHEN request executed, THEN state is Completed with CaseStudyUIDataNoData`() =
        runBlocking {
            coEvery { mockGetNewsArticlesUseCase.execute() } returns Result.Success(
                listOf()
            )

            subject.requestApi()

            assertThat(subject.getNewsArticlesStateNewsArticlesList.getOrAwaitValue()).isEqualTo(
                NewsArticlesListRequestState.Completed(
                    UICaseStudyList.NoCaseStudyList
                )
            )
        }

    @Test
    fun `requestApi() - given Result is Error then state returned is RequestStateError`() =
        runBlocking {
            coEvery { mockGetNewsArticlesUseCase.execute() } returns Result.Error

            subject.requestApi()

            assertThat(subject.getNewsArticlesStateNewsArticlesList.getOrAwaitValue() is NewsArticlesListRequestState.Error).isTrue()
        }

    @Test
    fun `navigateToDetailedView() - navigation state is set to NavigateToCaseStudyDetailedView`() {
        subject.navigateToDetailedView(item.newsArticleId)

        assertThat(subject.getNewsArticlesListNavigationState.getOrAwaitValue()).isEqualTo(
            NewsArticlesListNavigationState.NavigateToNewsArticleDetailedView(item.newsArticleId)
        )
    }

    @Test
    fun `monitorNetworkState() - verify registerNetworkCallback is called`() = runBlocking {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 24)

        subject.monitorNetworkState()

        verify { mockNetworkChecker.registerNetworkCallback() }
    }

    @Test
    fun `monitorNetworkState() - verify setupEvent is called`() {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 24)

        subject.monitorNetworkState()

        verify { mockNetworkChecker.setupEvent(subject) }
    }

    @Test
    fun `unregisterMonitoringOfNetworkState() - verify unregisterNetworkCallback is called`() {
        subject.unregisterMonitoringOfNetworkState()

        verify { mockNetworkChecker.unregisterNetworkCallback() }
    }

    @Test
    fun `onCaseStudyNetworkChanged() - given internet connection lost then state returned is EventStateNetworkStateLost`() =
        runBlocking {
            // network state is lost

            subject.onNetworkLost()

            assertThat(subject.getNewsArticlesListEventState.getOrAwaitValue() is NewsArticlesListEventState.NetworkStateLost).isTrue()
        }

    @Test
    fun `checkIfNetworkIsAvailable() - given network is not available then state returned is EventStateNetworkUnavailable`() =
        runBlocking {
            every { mockNetworkChecker.isNetworkAvailable() } returns false

            subject.checkIfNetworkIsAvailable()

            assertThat(subject.getNewsArticlesListEventState.getOrAwaitValue() is NewsArticlesListEventState.NetworkUnavailable).isTrue()
        }

    @After
    fun tearDown() {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), 0)
    }

    @Throws(Exception::class)
    fun setFinalStatic(field: Field, newValue: Any) {
        field.isAccessible = true

        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())

        field.set(null, newValue)
    }
}