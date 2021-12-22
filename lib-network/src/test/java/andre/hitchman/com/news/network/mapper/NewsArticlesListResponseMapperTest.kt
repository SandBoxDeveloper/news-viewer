package andre.hitchman.com.news.network.mapper

import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.network.mocks.NewsArticleResponseMock
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsArticlesListResponseMapperTest {

    private val expectedCaseStudyItem = NewsArticleItem.NewsArticleItemBuilder()
        .newsArticleId(NewsArticleResponseMock.id)
        .teaserText(NewsArticleResponseMock.teaserText)
        .newsArticleTitle(NewsArticleResponseMock.titleText)
        .heroImage(NewsArticleResponseMock.heroImageUrl)
        .sectionsList(NewsArticleResponseMock.SECTIONS_LIST)
        .build()

    private val subject by lazy { NewsArticlesListResponseMapper() }

    @Test
    fun `mapTo() - when response is mapped then list of case study items returned`() {
        val response = NewsArticleResponseMock.generateNewsArticles()

        val result = subject.fromResponseMapTo(response)

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(listOf(expectedCaseStudyItem))
    }

    @Test
    fun `mapTo() - when teaser text field is null, then null is mapped to CaseStudyItem object`() {
        val response = NewsArticleResponseMock.generateNewsArticles(teaser = null)

        val result = subject.fromResponseMapTo(response)

        assertThat(result).isEmpty()
    }

    @Test
    fun `mapTo() - when hero image field is empty, then empty string is mapped to CaseStudyItem object`() {
        val response = NewsArticleResponseMock.generateNewsArticles(heroUrl = "")

        val result = subject.fromResponseMapTo(response)

        assertThat(result).isNotNull()
        assertEquals("", result.first().heroImage)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun `mapTo() - when title field is empty, then empty string is mapped to CaseStudyItem object`() {
        val response = NewsArticleResponseMock.generateNewsArticles(title = "")

        val result = subject.fromResponseMapTo(response)

        assertThat(result).isNotNull()
        assertEquals("", result.first().newsArticleTitle)
        assertThat(result.size).isEqualTo(1)
    }
}