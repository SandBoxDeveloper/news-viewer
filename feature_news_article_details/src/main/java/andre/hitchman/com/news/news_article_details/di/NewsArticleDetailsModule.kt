package andre.hitchman.com.news.news_article_details.di

import andre.hitchman.com.news.news_article_details.repository.NewsArticleDetailsRepository
import andre.hitchman.com.news.news_article_details.repository.NewsArticleDetailsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsArticleDetailsModule {

    @Singleton
    @Provides
    fun provideNewsArticleDetailsRepository(
        newsArticleDetailsRepository: NewsArticleDetailsRepositoryImpl
    ): NewsArticleDetailsRepository = newsArticleDetailsRepository
}