package andre.hitchman.com.news.news_article_details.model

import andre.hitchman.com.news.model.NewsArticleItemSection

data class NewsArticleDetails(
    val id: Int,
    val title: String?,
    val sections: List<NewsArticleItemSection>
)