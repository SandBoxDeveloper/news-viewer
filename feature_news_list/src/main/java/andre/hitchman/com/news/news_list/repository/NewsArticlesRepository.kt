package andre.hitchman.com.news.news_list.repository

import andre.hitchman.com.news.news_list.strategy.GetNewsArticleListStrategy
import andre.hitchman.com.news.model.NewsArticleItem

interface NewsArticlesRepository {

    suspend fun getNewsArticles(
        newsArticleListStrategy: List<GetNewsArticleListStrategy>
    ): List<NewsArticleItem>
}