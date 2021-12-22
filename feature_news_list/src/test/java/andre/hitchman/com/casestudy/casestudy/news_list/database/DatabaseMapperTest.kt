package andre.hitchman.com.casestudy.casestudy.news_list.database

import andre.hitchman.com.casestudy.casestudy.news_list.mocks.CaseStudyResponseMock
import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.database.model.SectionItems
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.news_list.database.DatabaseMapper
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class DatabaseMapperTest {

    private val caseStudyItem = NewsArticleItem.NewsArticleItemBuilder()
        .newsArticleId(1)
        .teaserText("")
        .newsArticleTitle("")
        .heroImage("")
        .sectionsList(CaseStudyResponseMock.SECTIONS_LIST)
        .build()

    private val expectedCaseStudy = NewsArticle(
        newsArticleId = 1,
        title = "",
        teaser = "",
        heroImage = ""
    )

    private val firstExpectedSectionItem = SectionItems(
        id = 0,
        newsArticleId = 1,
        newsArticleSectionTitle = "section title",
        sectionText = "",
        sectionUrl = "www.hero.com"
    )

    private val secondExpectedSectionItem = SectionItems(
        id = 0,
        newsArticleId = 1,
        newsArticleSectionTitle = "section title",
        sectionText = "section",
        sectionUrl = ""
    )

    private val subject by lazy { DatabaseMapper() }

    @Test
    fun `mapToCaseStudyDatabaseObject() - when list of CaseStudyItems is mapped then list of CaseStudy items returned`() {
        val result = subject.mapToNewsArticleDatabaseObject(listOf(caseStudyItem))

        assertThat(result).isNotNull()
        assertEquals(listOf(expectedCaseStudy), result)
    }

    @Test
    fun `mapToSectionItemsDatabaseObject() - when list of CaseStudyItems is mapped then list of SectionItems is returned`() {
        val result = subject.mapToSectionItemsDatabaseObject(listOf(caseStudyItem))

        assertThat(result).isNotNull()
        assertEquals(listOf(firstExpectedSectionItem, secondExpectedSectionItem), result)
    }
}