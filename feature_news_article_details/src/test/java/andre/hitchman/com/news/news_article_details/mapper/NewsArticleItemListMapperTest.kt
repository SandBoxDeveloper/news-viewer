package andre.hitchman.com.news.news_article_details.mapper

import andre.hitchman.com.news.news_article_details.mocks.CaseStudyDetailsMock
import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.testutils.mocks.NewsArticleItemSectionBodyElementMock
import andre.hitchman.com.news.testutils.mocks.NewsArticleItemSectionMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NewsArticleItemListMapperTest {

    private val subject by lazy { NewsArticleItemListMapper() }

    @Test
    fun `mapTo() - when data items are mapped then list of case study details view data is returned`() {
        val dataItems = CaseStudyDetailsMock.generateCaseStudyDetails()

        val result = subject.fromListToUiData(dataItems)

        assertThat(result).isNotNull()
        assertThat(result).contains(UINewsArticleDetails.Title(NewsArticleItemSectionBodyElementMock.sectionTitle))
        assertThat(result).contains(
            UINewsArticleDetails.SectionBody(
                NewsArticleItemSectionBodyElementMock.sectionText
            )
        )
        assertThat(result).contains(
            UINewsArticleDetails.SectionImage(
                NewsArticleItemSectionBodyElementMock.sectionImageUrl
            )
        )
    }

    @Test
    fun `fromCaseStudyItemToViewData() - given NO section title then NO UICaseStudyDetailsTitle returned`() {
        val dataItems = CaseStudyDetailsMock.generateCaseStudyDetails(
            sections = NewsArticleItemSectionMock.generateCaseStudyItemSection(title = "")
        )

        val result = subject.fromListToUiData(dataItems)

        assertThat(result).doesNotContain(UINewsArticleDetails.Title(""))
    }

    @Test
    fun `fromCaseStudyItemToViewData() - given NO SectionText then NO UICaseStudyDetailsSectionBody returned`() {
        val dataItems = CaseStudyDetailsMock.generateCaseStudyDetails(
            sections = NewsArticleItemSectionMock.generateCaseStudyItemSection(
                body = NewsArticleItemSectionMock.generateListOfSectionBodyElement(
                    text = ""
                )
            )
        )

        val result = subject.fromListToUiData(dataItems)

        assertThat(result).doesNotContain(UINewsArticleDetails.SectionBody(""))
    }

    @Test
    fun `fromCaseStudyItemToViewData() - given NO SectionImage then NOUICaseStudyDetailsSectionImage returned`() {
        val dataItems = CaseStudyDetailsMock.generateCaseStudyDetails(
            sections = NewsArticleItemSectionMock.generateCaseStudyItemSection(
                body = NewsArticleItemSectionMock.generateListOfSectionBodyElement(
                    url = ""
                )
            )
        )
        val result = subject.fromListToUiData(dataItems)

        assertThat(result).doesNotContain(UINewsArticleDetails.HeroImage(""))
    }
}