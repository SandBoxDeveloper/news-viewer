package andre.hitchman.com.casestudy.casestudy.news_list.mocks

import andre.hitchman.com.news.model.NewsArticleItemSection
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import andre.hitchman.com.news.network.model.NewsArticlesListResponse
import andre.hitchman.com.news.network.model.NewsArticleResponse
import andre.hitchman.com.news.network.model.SectionResponse

object CaseStudyResponseMock {

    const val id: Int = 1
    const val teaserText: String = "the world according to Kotlin"
    const val titleText: String = "Kotlin World"
    const val heroImageUrl: String = "www.dummyurl.com"
    val SECTION_ITEMS: List<NewsArticleItemSectionBodyElement> =
        listOf(NewsArticleItemSectionBodyElement.ImageUrl("www.hero.com"), NewsArticleItemSectionBodyElement.SectionText("section"))
    val listOfSections: List<SectionResponse> =
        listOf(SectionResponse("section title", SECTION_ITEMS))
    val SECTIONS_LIST: List<NewsArticleItemSection> = listOf(NewsArticleItemSection("section title", SECTION_ITEMS))

    fun generateCaseStudies(
        teaser: String? = teaserText,
        title: String = titleText,
        heroUrl: String? = heroImageUrl,
        sections: List<SectionResponse> = listOfSections
    ) = NewsArticlesListResponse(
        listOf(mockCaseStudy(teaser, title, heroUrl, sections))
    )

    fun mockCaseStudy(
        teaser: String?,
        title: String,
        heroUrl: String?,
        sections: List<SectionResponse>
    ) = NewsArticleResponse(
        id = id,
        teaserText = teaser,
        title = title,
        heroImageUrl = heroUrl,
        sections = sections
    )
}