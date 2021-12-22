package andre.hitchman.com.news.news_list.livedatastate

sealed class NewsArticlesListEventState {

    object NetworkUnavailable : NewsArticlesListEventState()
    object NetworkStateLost : NewsArticlesListEventState()
}