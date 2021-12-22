package andre.hitchman.com.news.testutils.mocks

import andre.hitchman.com.news.model.NewsArticleItemSection
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement

object NewsArticleItemSectionMock {

    const val sectionText: String = "Kotlin World"
    const val sectionImageUrl: String = "www.dummyurl.com"

    fun generateCaseStudyItemSection(
        title: String? = NewsArticleItemSectionBodyElementMock.sectionTitle,
        body: List<NewsArticleItemSectionBodyElement> = generateListOfSectionBodyElement()
    ) = listOf(
        mockCaseStudyItemSection(title, body)
    )

    fun mockCaseStudyItemSection(
        sectionDataTitle: String?,
        sectionDataBody: List<NewsArticleItemSectionBodyElement>
    ) = NewsArticleItemSection(
        title = sectionDataTitle,
        body = sectionDataBody
    )

    fun generateListOfSectionBodyElement(
        text: String = sectionText,
        url: String = sectionImageUrl
    ) = listOf(mockSectionText(text), mockSectionImageUrl(url))

    private fun mockSectionText(text: String) = NewsArticleItemSectionBodyElement.SectionText(
        sectionText = text
    )

    private fun mockSectionImageUrl(imageUrl: String) = NewsArticleItemSectionBodyElement.ImageUrl(
        url = imageUrl
    )
}