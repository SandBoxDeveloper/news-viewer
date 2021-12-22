package andre.hitchman.com.news.news_article_details.repository

import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails

interface NewsArticleDetailsRepository {

    suspend fun getNewsArticleDetails(newsArticleId: Int): NewsArticleDetails
}