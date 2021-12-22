package andre.hitchman.com.news.news_article_details.mapper

import andre.hitchman.com.news.news_article_details.mocks.CaseStudyDetailsMock
import andre.hitchman.com.news.news_article_details.mocks.CaseStudyResponseMock
import andre.hitchman.com.news.news_article_details.mocks.DatabaseSectionItemsMock
import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsArticlesSectionDaoMapperTest {

    private val expectedCaseStudyDetails = NewsArticleDetails(
        id = CaseStudyResponseMock.id,
        title = CaseStudyResponseMock.titleText,
        sections = CaseStudyDetailsMock.generateCaseStudyDetails().sections
    )

    private val subject by lazy { NewsArticlesSectionDaoMapper() }

    @Test
    fun `mapTo() - when data is mapped then CaseStudyDetails is returned`() {
        val result = subject.mapToNewsArticleDetails(
            newsArticleId = CaseStudyResponseMock.id,
            newsArticleTitle = CaseStudyResponseMock.titleText,
            sectionItems = DatabaseSectionItemsMock.generateSectionItemsList()
        )

        assertEquals(expectedCaseStudyDetails, result)
    }

    @Test
    fun `mapTo() - given data with empty list of SectionItems, when response is mapped then list of case study items with empty list of sections is returned`() {
        val result = subject.mapToNewsArticleDetails(
            newsArticleId = CaseStudyResponseMock.id,
            newsArticleTitle = CaseStudyResponseMock.titleText,
            sectionItems = DatabaseSectionItemsMock.generateSectionItemsList()
        )

        assertEquals(expectedCaseStudyDetails.sections, result.sections)
    }

    @Test
    fun `mapTo() - given data with SectionItems and Title is empty then CaseStudyItem with sections title is empty`() {
        val sectionData = DatabaseSectionItemsMock.generateSectionItemsList(title = "")

        val result = subject.mapToNewsArticleDetails(
            newsArticleId = CaseStudyResponseMock.id,
            newsArticleTitle = CaseStudyResponseMock.titleText,
            sectionItems = sectionData
        )

        assertThat(result.sections[0].title).isEmpty()
    }

    @Test
    fun `mapTo() - given data with SectionItems and its body contains no sectionText then CaseStudyItem with sections body, SectionTextsectionText is empty`() {
        val sectionData = DatabaseSectionItemsMock.generateSectionItemsList(text = "")

        val result = subject.mapToNewsArticleDetails(
            newsArticleId = CaseStudyResponseMock.id,
            newsArticleTitle = CaseStudyResponseMock.titleText,
            sectionItems = sectionData
        )

        assertThat((result.sections[0].body[0] as NewsArticleItemSectionBodyElement.SectionText).sectionText).isEmpty()
    }

    @Test
    fun `mapTo() - given data with SectionItems and its body contains no sectionUrl then CaseStudyItem with sections body, ImageUrlurl is empty`() {
        val sectionData = DatabaseSectionItemsMock.generateSectionItemsList(url = "")

        val result = subject.mapToNewsArticleDetails(
            newsArticleId = CaseStudyResponseMock.id,
            newsArticleTitle = CaseStudyResponseMock.titleText,
            sectionItems = sectionData
        )

        assertThat((result.sections[0].body[1] as NewsArticleItemSectionBodyElement.ImageUrl).url).isEmpty()
    }
}