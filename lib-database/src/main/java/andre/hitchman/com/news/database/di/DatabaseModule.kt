package andre.hitchman.com.news.database.di

import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.database.newsarticles.NewsArticlesDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsArticlesDatabase(
        @ApplicationContext context: Context
    ): NewsArticlesDatabase = Room.databaseBuilder(
        context, NewsArticlesDatabase::class.java,
        "news_articles_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideNewsArticlesDao(
        newsArticlesDatabase: NewsArticlesDatabase
    ): NewsArticleDao = newsArticlesDatabase.newsArticleDao
}