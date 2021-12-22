package andre.hitchman.com.news.news_list.livedatastate

sealed class NewsArticlesListNavigationState {

    data class NavigateToNewsArticleDetailedView(
        val newsArticleItemId: Int
    ) : NewsArticlesListNavigationState()
}