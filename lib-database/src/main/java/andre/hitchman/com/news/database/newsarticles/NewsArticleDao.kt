package andre.hitchman.com.news.database.newsarticles

import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.database.model.SectionItems
import androidx.room.*

@Dao
interface NewsArticleDao {

    /* INSERT */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        newsArticles: List<NewsArticle>,
        sectionItems: List<SectionItems>
    )

    /* SELECT */

    @Query("SELECT * FROM SectionItems WHERE newsArticleId = :id")
    suspend fun getSectionItems(id: Int): List<SectionItems>?

    @Query("SELECT * FROM NewsArticle WHERE newsArticleId = :id")
    suspend fun getNewsArticle(id: Int): List<NewsArticle>?

    @Query("SELECT title FROM NewsArticle WHERE newsArticleId = :id")
    suspend fun getNewsArticleTitle(id: Int): String?

    @Query("SELECT * FROM NewsArticle")
    suspend fun getAllNewsArticles(): List<NewsArticle>?

    /* DELETE */

    @Query("DELETE FROM NewsArticle")
    suspend fun deleteAllNewsArticleData()

    @Query("DELETE FROM SectionItems")
    suspend fun deleteAllSectionItems()

    @Transaction
    suspend fun updateDatabaseWithNewData(
        newsArticles: List<NewsArticle>,
        sectionItems: List<SectionItems>
    ) {
        deleteAllNewsArticleData()
        deleteAllSectionItems()
        insertAll(newsArticles, sectionItems)
    }
}