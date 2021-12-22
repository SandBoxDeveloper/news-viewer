package andre.hitchman.com.news.news_article_details.livedatastate

import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails

sealed class NewsArticleDetailsRequestState {

    object Loading : NewsArticleDetailsRequestState()

    data class Data(val newsArticleDetails: NewsArticleDetails) : NewsArticleDetailsRequestState()

    object NoData : NewsArticleDetailsRequestState()

    object Error : NewsArticleDetailsRequestState()
}