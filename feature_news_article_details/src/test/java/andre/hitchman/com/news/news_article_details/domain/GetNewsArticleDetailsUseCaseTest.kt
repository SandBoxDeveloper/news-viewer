package andre.hitchman.com.news.news_article_details.domain

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_article_details.mocks.CaseStudyDetailsMock
import andre.hitchman.com.news.news_article_details.repository.NewsArticleDetailsRepository
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.testutils.rules.MockKRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetNewsArticleDetailsUseCaseTest {

    private companion object {
        val EXPECTED_RESULT = CaseStudyDetailsMock.generateCaseStudyDetails()
    }

    @get:Rule
    val mockKRule = MockKRule()

    @RelaxedMockK
    private lateinit var mockNewsArticleDetailsRepository: NewsArticleDetailsRepository

    private lateinit var subject: GetNewsArticleDetailsUseCase

    @Before
    fun setUp() {
        subject = GetNewsArticleDetailsUseCase(mockNewsArticleDetailsRepository)
    }

    @Test
    fun `execute() - success, then ResultSuccess returned with list of CaseStudyItem`() =
        runBlocking {
            coEvery {
                mockNewsArticleDetailsRepository.getNewsArticleDetails(newsArticleId = 1)
            } returns EXPECTED_RESULT

            val result = subject.execute(newsArticleId = 1)

            assertThat(result is Result.Success).isTrue()
            assertThat((result as Result.Success).data).isEqualTo(EXPECTED_RESULT)
        }

    @Test
    fun `execute() - exception, then ResultError returned`() =
        runBlocking {
            coEvery {
                mockNewsArticleDetailsRepository.getNewsArticleDetails(newsArticleId = 1)
            } throws RemoteNetworkException()

            val result = subject.execute(newsArticleId = 1)

            assertThat(result is Result.Error).isTrue()
        }

    @Test(expected = CancellationException::class)
    fun `execute() - cancellation exception, then ResultError returned`() =
        runBlocking {
            coEvery {
                mockNewsArticleDetailsRepository.getNewsArticleDetails(newsArticleId = 1)
            } throws CancellationException()

            subject.execute(newsArticleId = 1)

            return@runBlocking
        }
}