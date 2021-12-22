package andre.hitchman.com.news.news_list.strategy

import andre.hitchman.com.news.news_list.mapper.NewsArticleDaoMapper
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.core.coroutine.CoroutineDispatcherProvider
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.model.NewsArticleItem
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsArticlesListOfflineStrategy @Inject constructor(
    private val database: NewsArticleDao,
    private val newsArticleDaoMapper: NewsArticleDaoMapper
) : GetNewsArticleListStrategy {

    override suspend fun getNewsArticlesList(): List<NewsArticleItem> {
        try {
            val response = withContext(CoroutineDispatcherProvider.io) {
                database.getAllNewsArticles()
            }
            return newsArticleDaoMapper.fromResponseTo(response)
        } catch (exception: Exception) {
            throw RemoteNetworkException()
        }
    }
}