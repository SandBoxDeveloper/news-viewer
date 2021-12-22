package andre.hitchman.com.news.testutils.mocks

import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.model.NewsArticleItemSection

object NewsArticleItemMock {

    const val newsArticleItemId: Int = 1
    const val teaserText: String = "the world according to Kotlin"
    const val titleText: String = "Kotlin World"
    const val heroImageUrl: String = "www.dummyurl.com"

    fun generateNewsArticleItemList(
        id: Int = newsArticleItemId,
        teaser: String? = teaserText,
        title: String = titleText,
        url: String = heroImageUrl,
        sections: List<NewsArticleItemSection> = NewsArticleItemSectionMock.generateCaseStudyItemSection()
    ) = listOf(mockNewsArticleItem(id, teaser, title, url, sections))

    fun mockNewsArticleItem(
        id: Int,
        teaser: String?,
        title: String,
        url: String,
        sections: List<NewsArticleItemSection>
    ) = NewsArticleItem.NewsArticleItemBuilder()
        .newsArticleId(id)
        .teaserText(teaser)
        .newsArticleTitle(title)
        .heroImage(url)
        .sectionsList(sections)
        .build()
}