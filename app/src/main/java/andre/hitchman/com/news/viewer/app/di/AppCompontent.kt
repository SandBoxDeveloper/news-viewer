package andre.hitchman.com.news.viewer.app.di

import andre.hitchman.com.news.base.di.ImageLoaderModule
import andre.hitchman.com.news.news_list.di.NewsArticlesListModule
import andre.hitchman.com.news.news_article_details.di.NewsArticleDetailsModule
import andre.hitchman.com.news.database.di.DatabaseModule
import andre.hitchman.com.news.network.di.NetworkModule
import andre.hitchman.com.news.repository.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NewsArticlesListModule::class,
        NewsArticleDetailsModule::class,
        DatabaseModule::class,
        ImageLoaderModule::class,
        NetworkModule::class,
        RepositoryModule::class]
)
interface AppComponent

