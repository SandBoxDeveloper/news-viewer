package andre.hitchman.com.news.testutils.mocks

import andre.hitchman.com.news.model.NewsArticleItemSection
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement

object NewsArticleItemSectionBodyElementMock {

    const val newsArticleId: Int = 1
    const val sectionTitle: String = "the world according to Kotlin"
    const val sectionText: String = "Kotlin World"
    const val sectionImageUrl: String = "www.dummyurl.com"

    fun generateSectionItemsList(
        title: String? = sectionTitle,
        text: String? = sectionText,
        url: String? = sectionImageUrl
    ) = listOf(mockSectionItems(title, text, url))

    fun mockSectionItems(
        title: String?,
        text: String?,
        url: String?
    ) = NewsArticleItemSection(
        title = title,
        body = listOf(
            mockCaseStudyItemSectionBodyElementImageUrl(url),
            mockCaseStudyItemSectionBodyElementSectionText(text)
        )
    )

    fun mockCaseStudyItemSectionBodyElementImageUrl(
        bodyElementUrl: String?,
    ) = NewsArticleItemSectionBodyElement.ImageUrl(
        url = if (bodyElementUrl.isNullOrEmpty()) {
            sectionImageUrl
        } else {
            bodyElementUrl
        }
    )

    fun mockCaseStudyItemSectionBodyElementSectionText(
        bodyElementSectionText: String?,
    ) = NewsArticleItemSectionBodyElement.SectionText(
        sectionText = if (bodyElementSectionText.isNullOrEmpty()) {
            sectionText
        } else {
            bodyElementSectionText
        }
    )
}