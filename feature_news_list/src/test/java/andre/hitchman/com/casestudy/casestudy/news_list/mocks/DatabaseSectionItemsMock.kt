package andre.hitchman.com.casestudy.casestudy.news_list.mocks

import andre.hitchman.com.news.database.model.SectionItems

object DatabaseSectionItemsMock {

    const val sectionId: Int = 1
    const val newsArticleId: Int = 1
    const val sectionTitle: String = "the world according to Kotlin"
    const val sectionText: String = "Kotlin World"
    const val sectionImageUrl: String = "www.dummyurl.com"

    fun generateSectionItemsList(
        id: Int = sectionId,
        newsArticleItemId: Int = newsArticleId,
        title: String? = sectionTitle,
        text: String? = sectionText,
        url: String? = sectionImageUrl
    ) = listOf(mockSectionItems(id, newsArticleItemId, title, text, url))

    fun mockSectionItems(
        id: Int,
        newsArticleId: Int,
        title: String?,
        text: String?,
        url: String?
    ) = SectionItems(
        id = id,
        newsArticleId = newsArticleId,
        newsArticleSectionTitle = title,
        sectionText = text,
        sectionUrl = url
    )
}