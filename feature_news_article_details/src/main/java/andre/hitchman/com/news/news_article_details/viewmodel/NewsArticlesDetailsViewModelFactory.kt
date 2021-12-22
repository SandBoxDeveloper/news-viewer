package andre.hitchman.com.news.news_article_details.viewmodel

import andre.hitchman.com.news.news_article_details.domain.GetNewsArticleDetailsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class NewsArticlesDetailsViewModelFactory @Inject constructor(
    private val getNewsArticleDetailsUseCase: GetNewsArticleDetailsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsArticlesDetailsViewModel::class.java)) {
            return NewsArticlesDetailsViewModel(getNewsArticleDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}