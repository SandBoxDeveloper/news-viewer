package andre.hitchman.com.news.news_list.repository

import andre.hitchman.com.news.news_list.strategy.GetNewsArticleListStrategy
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.model.NewsArticleItem
import javax.inject.Inject

class NewsArticlesListRepositoryImpl @Inject constructor() : NewsArticlesRepository {

    var newsArticles: List<NewsArticleItem> = emptyList()

    override suspend fun getNewsArticles(
        newsArticleListStrategy: List<GetNewsArticleListStrategy>
    ): List<NewsArticleItem> {
        newsArticleListStrategy.forEach {
            try {
                newsArticles = it.getNewsArticlesList()
            } catch (exception: RemoteNetworkException) {
                throw RemoteNetworkException()
            }
        }

        return newsArticles
    }
}