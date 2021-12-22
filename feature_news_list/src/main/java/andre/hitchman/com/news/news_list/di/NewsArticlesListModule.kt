package andre.hitchman.com.news.news_list.di

import andre.hitchman.com.news.base.image.GlideImageLoader
import andre.hitchman.com.news.base.image.ImageLoader
import andre.hitchman.com.news.news_list.repository.NewsArticlesListRepositoryImpl
import andre.hitchman.com.news.news_list.repository.NewsArticlesRepository
import andre.hitchman.com.news.news_list.view.NewsArticlesAdapter
import andre.hitchman.com.news.repository.networkcall.SafeNetworkCallDelegate
import andre.hitchman.com.news.repository.networkcall.SafeNetworkCallDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsArticlesListModule {

    @Provides
    @Singleton
    fun provideNewsArticlesRepository(
        newsArticlesListRepository: NewsArticlesListRepositoryImpl
    ): NewsArticlesRepository = newsArticlesListRepository

    @Singleton
    @Provides
    fun provideGlideImageLoader(
        glideImageLoader: GlideImageLoader
    ): ImageLoader = glideImageLoader

    @Provides
    @Singleton
    fun provideNewsArticlesAdapter(
        imageLoader: ImageLoader
    ): NewsArticlesAdapter = NewsArticlesAdapter(imageLoader)

    @Provides
    @Singleton
    fun provideSafeNetworkCallDelegate(
        safeNetworkCallDelegate: SafeNetworkCallDelegateImpl
    ): SafeNetworkCallDelegate = safeNetworkCallDelegate
}