package andre.hitchman.com.news.network.mapper

import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.model.NewsArticleItemSection
import andre.hitchman.com.news.network.model.NewsArticlesListResponse
import andre.hitchman.com.news.network.model.SectionResponse
import javax.inject.Inject

class NewsArticlesListResponseMapper @Inject constructor() {

    fun fromResponseMapTo(newsArticlesResponseListResponse: NewsArticlesListResponse?): List<NewsArticleItem> {

        val listOfNewsArticlesWithTeaserTextIncluded =
            newsArticlesResponseListResponse?.newsArticles?.filter {
                !it.teaserText.isNullOrBlank()
            }

        return listOfNewsArticlesWithTeaserTextIncluded?.map {
            NewsArticleItem.NewsArticleItemBuilder()
                .newsArticleId(it.id)
                .teaserText(it.teaserText)
                .newsArticleTitle(it.title)
                .heroImage(it.heroImageUrl)
                .sectionsList(it.sections.toSections())
                .build()
        } ?: emptyList()
    }

    private fun List<SectionResponse>.toSections(): List<NewsArticleItemSection> {
        return this.map {
            NewsArticleItemSection(
                title = it.title,
                body = it.bodyElements
            )
        }
    }
}