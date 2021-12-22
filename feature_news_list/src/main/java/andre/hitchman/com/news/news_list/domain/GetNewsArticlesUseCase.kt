package andre.hitchman.com.news.news_list.domain

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_list.repository.NewsArticlesRepository
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListNetworkStrategy
import andre.hitchman.com.news.news_list.strategy.GetNewsArticlesListOfflineStrategy
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.network.NetworkChecker
import javax.inject.Inject

class GetNewsArticlesUseCase @Inject constructor(
    private val newsArticlesRepository: NewsArticlesRepository,
    private val getNewsArticlesListNetworkStrategy: GetNewsArticlesListNetworkStrategy,
    private val getNewsArticlesListOfflineStrategy: GetNewsArticlesListOfflineStrategy,
    private val networkChecker: NetworkChecker
) {

    suspend fun execute(): Result<List<NewsArticleItem>> {
        return try {
            println("Getting News Articles!")
            val result = if (networkChecker.isNetworkAvailable()) {
                newsArticlesRepository.getNewsArticles(
                    newsArticleListStrategy = listOf(getNewsArticlesListNetworkStrategy)
                )
            } else {
                newsArticlesRepository.getNewsArticles(
                    newsArticleListStrategy = listOf(getNewsArticlesListOfflineStrategy)
                )
            }
            Result.Success(result)
        } catch (remoteNetworkException: RemoteNetworkException) {
            Result.Error
        }
    }
}