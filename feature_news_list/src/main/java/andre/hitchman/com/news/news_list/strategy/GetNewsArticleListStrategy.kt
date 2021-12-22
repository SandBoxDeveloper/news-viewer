package andre.hitchman.com.news.news_list.strategy

import andre.hitchman.com.news.model.NewsArticleItem

interface GetNewsArticleListStrategy {

    suspend fun getNewsArticlesList(): List<NewsArticleItem>
}