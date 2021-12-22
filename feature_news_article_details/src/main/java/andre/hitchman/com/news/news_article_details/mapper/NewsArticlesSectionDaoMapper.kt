package andre.hitchman.com.news.news_article_details.mapper

import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.database.model.SectionItems
import andre.hitchman.com.news.model.NewsArticleItemSection
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import javax.inject.Inject

class NewsArticlesSectionDaoMapper @Inject constructor() {

    fun mapToNewsArticleDetails(
        newsArticleId: Int,
        newsArticleTitle: String?,
        sectionItems: List<SectionItems>?
    ): NewsArticleDetails {
        return NewsArticleDetails(
            id = newsArticleId,
            title = newsArticleTitle,
            sections = toSectionDataItem(sectionItems)
        )
    }

    private fun toSectionDataItem(response: List<SectionItems>?): List<NewsArticleItemSection> {
        val sectionDataItem = mutableListOf<NewsArticleItemSection>()
        val allSectionItemsForNewsArticleTitle = response?.distinctBy { it.newsArticleSectionTitle }

        allSectionItemsForNewsArticleTitle?.forEach { items ->
            val sectionItemsContent: List<SectionItems> =
                response.filter { sectionDataItem -> sectionDataItem.newsArticleSectionTitle == items.newsArticleSectionTitle }
            sectionDataItem.add(
                NewsArticleItemSection(
                    title = items.newsArticleSectionTitle,
                    body = getBodyContentList(sectionItemsContent)
                )
            )
        }

        return sectionDataItem
    }

    private fun getBodyContentList(images: List<SectionItems>): MutableList<NewsArticleItemSectionBodyElement> {
        val sectionTextList = mutableListOf<NewsArticleItemSectionBodyElement>()
        images.forEach {
            sectionTextList.add(NewsArticleItemSectionBodyElement.SectionText(it.sectionText ?: ""))
            sectionTextList.add(NewsArticleItemSectionBodyElement.ImageUrl(it.sectionUrl ?: ""))
        }
        return sectionTextList
    }
}