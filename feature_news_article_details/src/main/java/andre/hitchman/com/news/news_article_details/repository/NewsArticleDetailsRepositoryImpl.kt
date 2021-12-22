package andre.hitchman.com.news.news_article_details.repository

import andre.hitchman.com.news.news_article_details.mapper.NewsArticlesSectionDaoMapper
import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.core.coroutine.CoroutineDispatcherProvider
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsArticleDetailsRepositoryImpl @Inject constructor(
    private val database: NewsArticleDao,
    private val newsArticlesSectionDaoMapper: NewsArticlesSectionDaoMapper
) : NewsArticleDetailsRepository {

    override suspend fun getNewsArticleDetails(newsArticleId: Int): NewsArticleDetails {
        try {
            //TODO: what if the data returned is empty, then use empty state screen
            val response = withContext(CoroutineDispatcherProvider.io) {
                database.getSectionItems(newsArticleId)
            }
            val title = withContext(CoroutineDispatcherProvider.io) {
                database.getNewsArticleTitle(newsArticleId)
            }
            return newsArticlesSectionDaoMapper.mapToNewsArticleDetails(newsArticleId, title, response)
        } catch (exception: Exception) {
            throw RemoteNetworkException()
        }
    }
}