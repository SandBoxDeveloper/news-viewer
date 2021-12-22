package andre.hitchman.com.news.news_list.database

import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.database.model.SectionItems
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import javax.inject.Inject

class DatabaseMapper @Inject constructor() {

    lateinit var sectionItems: SectionItems

    fun mapToNewsArticleDatabaseObject(data: List<NewsArticleItem>): List<NewsArticle> {
        return data.map {
            NewsArticle(
                newsArticleId = it.newsArticleId,
                teaser = it.teaserText,
                title = it.newsArticleTitle,
                heroImage = it.heroImage
            )
        }
    }

    fun mapToSectionItemsDatabaseObject(newsArticles: List<NewsArticleItem>): List<SectionItems> {
        val listOfSectionItems = mutableListOf<SectionItems>()

        newsArticles.forEachIndexed { _, newsArticleItem ->
            newsArticleItem.sectionsList?.forEach { sectionDataItem ->
                sectionItems = SectionItems(
                    newsArticleId = newsArticleItem.newsArticleId,
                    newsArticleSectionTitle = sectionDataItem.title,
                    sectionText = "",
                    sectionUrl = ""
                )

                sectionDataItem.body.forEach { sectionItem ->
                    listOfSectionItems.add(
                        sectionItems.copy(
                            sectionText = getSectionText(sectionItem),
                            sectionUrl = getSectionUrl(sectionItem)
                        )
                    )
                }
            }
        }

        return listOfSectionItems
    }

    private fun getSectionText(sectionItem: NewsArticleItemSectionBodyElement): String {

        return when (sectionItem) {
            is NewsArticleItemSectionBodyElement.ImageUrl -> ""
            is NewsArticleItemSectionBodyElement.SectionText -> sectionItem.sectionText
        }
    }

    private fun getSectionUrl(sectionItem: NewsArticleItemSectionBodyElement): String {

        return when (sectionItem) {
            is NewsArticleItemSectionBodyElement.ImageUrl -> sectionItem.url
            is NewsArticleItemSectionBodyElement.SectionText -> ""
        }
    }
}
