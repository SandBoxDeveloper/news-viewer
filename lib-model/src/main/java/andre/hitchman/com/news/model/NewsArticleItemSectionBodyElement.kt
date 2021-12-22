package andre.hitchman.com.news.model

sealed class NewsArticleItemSectionBodyElement {

    data class SectionText(
        val sectionText: String
    ) : NewsArticleItemSectionBodyElement()

    data class ImageUrl(
        val url: String
    ) : NewsArticleItemSectionBodyElement()
}