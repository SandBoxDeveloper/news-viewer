package andre.hitchman.com.news.base.di

import andre.hitchman.com.news.base.image.GlideImageLoader
import andre.hitchman.com.news.base.image.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Singleton
    @Provides
    fun provideGlideImageLoader(
        glideImageLoader: GlideImageLoader
    ): ImageLoader = glideImageLoader
}