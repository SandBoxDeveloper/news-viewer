package andre.hitchman.com.news.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsArticle(
    @PrimaryKey @ColumnInfo(name = "newsArticleId") val newsArticleId: Int,
    @ColumnInfo(name = "teaser") val teaser: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "hero_image") val heroImage: String?
)

@Entity
data class SectionItems(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "sectionItemId") val id: Int = 0,
    @ColumnInfo(name = "newsArticleId") val newsArticleId: Int,
    @ColumnInfo(name = "newsArticleSectionTitle") val newsArticleSectionTitle: String?,
    @ColumnInfo(name = "sectionText") val sectionText: String?,
    @ColumnInfo(name = "sectionUrl") val sectionUrl: String?
)