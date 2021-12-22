package andre.hitchman.com.news.network.di

import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import andre.hitchman.com.news.network.BuildConfig
import andre.hitchman.com.news.network.NetworkChecker
import andre.hitchman.com.news.network.deserializer.SectionDeserializer
import andre.hitchman.com.news.network.service.NewsArticlesService
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSectionDeserializer(): SectionDeserializer = SectionDeserializer()

    @Provides
    @Singleton
    fun provideGson(
        sectionDeserializer: SectionDeserializer
    ): Gson = GsonBuilder()
        .registerTypeAdapter(NewsArticleItemSectionBodyElement::class.java, sectionDeserializer)
        .create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(
        gson: Gson
    ): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCaseStudyService(retrofit: Retrofit): NewsArticlesService =
        retrofit.create(NewsArticlesService::class.java)

    @Singleton
    @Provides
    fun provideNetworkChecker(
        @ApplicationContext applicationContext: Context
    ): NetworkChecker = NetworkChecker(applicationContext)
}