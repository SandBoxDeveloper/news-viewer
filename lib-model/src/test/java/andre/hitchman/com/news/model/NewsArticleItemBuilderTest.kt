package andre.hitchman.com.news.model

import andre.hitchman.com.news.testutils.mocks.NewsArticleItemMock
import andre.hitchman.com.news.testutils.mocks.NewsArticleItemSectionMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NewsArticleItemBuilderTest {

    private val subject by lazy { NewsArticleItem.NewsArticleItemBuilder() }

    @Test
    fun `build() - given all field parameters provided then CaseStudyItem is return with the provided fields`() {
        val expectedSectionItemsList = NewsArticleItemSectionMock.generateCaseStudyItemSection()
        val result = subject
            .newsArticleId(NewsArticleItemMock.newsArticleItemId)
            .teaserText(NewsArticleItemMock.teaserText)
            .newsArticleTitle(NewsArticleItemMock.titleText)
            .heroImage(NewsArticleItemMock.heroImageUrl)
            .sectionsList(NewsArticleItemSectionMock.generateCaseStudyItemSection())
            .build()

        assertThat(result.newsArticleId).isEqualTo(NewsArticleItemMock.newsArticleItemId)
        assertThat(result.teaserText).isEqualTo(NewsArticleItemMock.teaserText)
        assertThat(result.newsArticleTitle).isEqualTo(NewsArticleItemMock.titleText)
        assertThat(result.heroImage).isEqualTo(NewsArticleItemMock.heroImageUrl)
        assertThat(result.sectionsList).isEqualTo(expectedSectionItemsList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `build() - given id field is missing then IllegalArgumentException is thrown`() {
        subject
            .teaserText(NewsArticleItemMock.teaserText)
            .newsArticleTitle(NewsArticleItemMock.titleText)
            .heroImage(NewsArticleItemMock.heroImageUrl)
            .sectionsList(NewsArticleItemSectionMock.generateCaseStudyItemSection())
            .build()
    }

    @Test
    fun `build() - given title field is missing then default value null is set`() {
        val result = subject
            .newsArticleId(NewsArticleItemMock.newsArticleItemId)
            .teaserText(NewsArticleItemMock.teaserText)
            .heroImage(NewsArticleItemMock.heroImageUrl)
            .sectionsList(NewsArticleItemSectionMock.generateCaseStudyItemSection())
            .build()

        assertThat(result.newsArticleTitle).isNull()
    }

    @Test
    fun `build() - given teaser text field is missing then default value null is set`() {
        val result = subject
            .newsArticleId(NewsArticleItemMock.newsArticleItemId)
            .newsArticleTitle(NewsArticleItemMock.titleText)
            .heroImage(NewsArticleItemMock.heroImageUrl)
            .sectionsList(NewsArticleItemSectionMock.generateCaseStudyItemSection())
            .build()

        assertThat(result.teaserText).isNull()
    }

    @Test
    fun `build() - given hero image url field is missing then default value null is set`() {
        val result = subject
            .newsArticleId(NewsArticleItemMock.newsArticleItemId)
            .teaserText(NewsArticleItemMock.teaserText)
            .newsArticleTitle(NewsArticleItemMock.titleText)
            .sectionsList(NewsArticleItemSectionMock.generateCaseStudyItemSection())
            .build()

        assertThat(result.heroImage).isNull()
    }

    @Test
    fun `build() - given sections list field is missing then default value emptyList() is set`() {
        val result = subject
            .newsArticleId(NewsArticleItemMock.newsArticleItemId)
            .teaserText(NewsArticleItemMock.teaserText)
            .newsArticleTitle(NewsArticleItemMock.titleText)
            .heroImage(NewsArticleItemMock.heroImageUrl)
            .build()

        assertThat(result.sectionsList).isEqualTo(emptyList<NewsArticleItemSection>())
    }
}