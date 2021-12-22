package andre.hitchman.com.casestudy.casestudy.news_list.mapper

import andre.hitchman.com.casestudy.casestudy.news_list.mocks.CaseStudyResponseMock
import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.news_list.mapper.NewsArticleDaoMapper
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsArticleDaoMapperTest {

    private val expectedCaseStudyItem = NewsArticleItem.NewsArticleItemBuilder()
        .newsArticleId(CaseStudyResponseMock.id)
        .teaserText(CaseStudyResponseMock.teaserText)
        .newsArticleTitle(CaseStudyResponseMock.titleText)
        .heroImage(CaseStudyResponseMock.heroImageUrl)
        .sectionsList(emptyList())
        .build()

    private val subject by lazy { NewsArticleDaoMapper() }

    @Test
    fun `mapTo() - when response is mapped then list of CaseStudyItem returned`() {
        val response = listOf(
            NewsArticle(
                newsArticleId = CaseStudyResponseMock.id,
                title = CaseStudyResponseMock.titleText,
                teaser = CaseStudyResponseMock.teaserText,
                heroImage = CaseStudyResponseMock.heroImageUrl
            )
        )

        val result = subject.fromResponseTo(response)

        assertThat(result).isNotNull()
        assertEquals(listOf(expectedCaseStudyItem), result)
    }

    @Test
    fun `mapTo() - given response is empty when mapped then list of empty list of type CaseStudyItem returned`() {
        val response = emptyList<NewsArticle>()

        val result = subject.fromResponseTo(response)

        assertThat(result).isNotNull()
        assertEquals(emptyList<NewsArticleItem>(), result)
    }
}