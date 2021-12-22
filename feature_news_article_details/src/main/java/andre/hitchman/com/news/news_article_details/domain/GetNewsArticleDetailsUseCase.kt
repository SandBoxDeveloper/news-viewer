package andre.hitchman.com.news.news_article_details.domain

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.news_article_details.repository.NewsArticleDetailsRepository
import andre.hitchman.com.news.core.RemoteNetworkException
import javax.inject.Inject

class GetNewsArticleDetailsUseCase @Inject constructor(
    private val newsArticleDetailsRepository: NewsArticleDetailsRepository
) {

    suspend fun execute(newsArticleId: Int): Result<NewsArticleDetails> {
        return try {
            println("Getting News Article Details!")
            val result = newsArticleDetailsRepository.getNewsArticleDetails(newsArticleId = newsArticleId)
            Result.Success(result)
        } catch (remoteNetworkException: RemoteNetworkException) {
            Result.Error
        }
    }
}