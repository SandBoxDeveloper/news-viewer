package andre.hitchman.com.news.news_article_details.model

sealed class UINewsArticleDetails(val type: Int) {

    object ViewType {
        const val TITLE = 0
        const val HERO_IMAGE = 1
        const val SECTION_BODY = 2
        const val SECTION_IMAGE = 3
    }

    data class Title(val title: String) : UINewsArticleDetails(ViewType.TITLE)

    data class HeroImage(val imageUrl: String) : UINewsArticleDetails(ViewType.HERO_IMAGE)

    data class SectionBody(val text: String) : UINewsArticleDetails(ViewType.SECTION_BODY)

    data class SectionImage(val url: String) : UINewsArticleDetails(ViewType.SECTION_IMAGE)
}