package andre.hitchman.com.news.news_article_details.mocks

import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.model.NewsArticleItemSection
import andre.hitchman.com.news.testutils.mocks.NewsArticleItemSectionMock

object CaseStudyDetailsMock {

    const val newsArticleItemId: Int = 1
    const val titleText: String = "Kotlin World"

    fun generateCaseStudyDetails(
        id: Int = newsArticleItemId,
        title: String = titleText,
        sections: List<NewsArticleItemSection> = NewsArticleItemSectionMock.generateCaseStudyItemSection()
    ) = mockCaseStudyDetails(id, title, sections)

    fun mockCaseStudyDetails(
        id: Int,
        title: String,
        sections: List<NewsArticleItemSection>
    ) = NewsArticleDetails(
        id = id,
        title = title,
        sections = sections
    )
}