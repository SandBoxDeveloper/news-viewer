package andre.hitchman.com.news.network.model

import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import com.google.gson.annotations.SerializedName

data class NewsArticlesListResponse(
    @SerializedName("articles")
    val newsArticles: List<NewsArticleResponse>
)

data class NewsArticleResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("teaser")
    val teaserText: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("hero_image")
    val heroImageUrl: String?,
    @SerializedName("sections")
    val sections: List<SectionResponse>
)

data class SectionResponse(
    @SerializedName("title")
    val title: String?,
    @SerializedName("body_elements")
    val bodyElements: List<NewsArticleItemSectionBodyElement>
)