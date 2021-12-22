package andre.hitchman.com.news.news_list.strategy

import andre.hitchman.com.news.news_list.database.DatabaseMapper
import andre.hitchman.com.news.core.RemoteNetworkException
import andre.hitchman.com.news.core.coroutine.CoroutineDispatcherProvider
import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.network.mapper.NewsArticlesListResponseMapper
import andre.hitchman.com.news.network.service.NewsArticlesService
import andre.hitchman.com.news.repository.networkcall.SafeNetworkCallDelegate
import javax.inject.Inject

class GetNewsArticlesListNetworkStrategy @Inject constructor(
    private val newsArticlesService: NewsArticlesService,
    private val newsArticlesListResponseMapper: NewsArticlesListResponseMapper,
    private val database: NewsArticleDao,
    private val databaseMapper: DatabaseMapper,
    private val safeNetworkCallDelegate: SafeNetworkCallDelegate
) : GetNewsArticleListStrategy, SafeNetworkCallDelegate by safeNetworkCallDelegate {

    override suspend fun getNewsArticlesList(): List<NewsArticleItem> {
        val response = executeSafeApiCall(
            networkRequest = { newsArticlesService.getNewsArticles() },
            coroutineContext = CoroutineDispatcherProvider.io
        )
        when {
            response.isSuccessful -> {
                val caseStudyItems = newsArticlesListResponseMapper.fromResponseMapTo(response.body())
                persistData(caseStudyItems)
                return caseStudyItems
            }
            else -> throw RemoteNetworkException()
        }
    }

    private suspend fun persistData(result: List<NewsArticleItem>) {
        val databaseItems = databaseMapper.mapToNewsArticleDatabaseObject(result)
        val sectionItems = databaseMapper.mapToSectionItemsDatabaseObject(result)
        database.updateDatabaseWithNewData(databaseItems, sectionItems)
    }
}