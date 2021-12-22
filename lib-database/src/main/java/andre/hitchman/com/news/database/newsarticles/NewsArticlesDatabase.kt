package andre.hitchman.com.news.database.newsarticles

import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.database.model.SectionItems
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsArticle::class, SectionItems::class],
    version = 1,
    exportSchema = false
)
abstract class NewsArticlesDatabase : RoomDatabase() {

    abstract val newsArticleDao: NewsArticleDao
}