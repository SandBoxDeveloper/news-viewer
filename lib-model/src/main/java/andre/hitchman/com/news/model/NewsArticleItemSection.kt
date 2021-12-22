package andre.hitchman.com.news.model

data class NewsArticleItemSection(
    val title: String?,
    val body: List<NewsArticleItemSectionBodyElement>
)