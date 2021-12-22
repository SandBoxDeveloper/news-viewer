package andre.hitchman.com.news.news_list.mapper

import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.model.NewsArticleItem
import javax.inject.Inject

class NewsArticleDaoMapper @Inject constructor() {

    fun fromResponseTo(newsArticles: List<NewsArticle>?): List<NewsArticleItem> {
        return newsArticles?.map {
            NewsArticleItem.NewsArticleItemBuilder()
                .newsArticleId(it.newsArticleId)
                .teaserText(it.teaser)
                .newsArticleTitle(it.title)
                .heroImage(it.heroImage)
                .sectionsList(emptyList())
                .build()
        } ?: emptyList()
    }
}