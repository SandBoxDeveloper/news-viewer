package andre.hitchman.com.news.network.service

import andre.hitchman.com.news.network.model.NewsArticlesListResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsArticlesService {

    //https://news-api-ktor-server.herokuapp.com/article/images
    @GET("images")
    suspend fun getNewsArticles(): Response<NewsArticlesListResponse>
}