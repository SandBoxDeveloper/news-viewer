package andre.hitchman.com.news.news_article_details.mapper

import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import javax.inject.Inject

class NewsArticleItemListMapper @Inject constructor() {

    fun fromListToUiData(newsArticleItems: NewsArticleDetails): List<UINewsArticleDetails> {
        val sectionsList = mutableListOf<UINewsArticleDetails>()

        newsArticleItems.sections.forEach {
            val title = it.title
            if (!title.isNullOrBlank()) {
                if (!sectionsList.contains(UINewsArticleDetails.Title(title))) {
                    sectionsList.add(UINewsArticleDetails.Title(title))
                }
            }

            it.body.forEach {
                when (it) {
                    is NewsArticleItemSectionBodyElement.SectionText -> {
                        if (it.sectionText.isNotBlank()) {
                            sectionsList.add(UINewsArticleDetails.SectionBody(it.sectionText))
                        }
                    }

                    is NewsArticleItemSectionBodyElement.ImageUrl -> {
                        if (it.url.isNotBlank()) {
                            sectionsList.add(UINewsArticleDetails.SectionImage(it.url))
                        }
                    }
                }
            }

        }

        return sectionsList
    }
}