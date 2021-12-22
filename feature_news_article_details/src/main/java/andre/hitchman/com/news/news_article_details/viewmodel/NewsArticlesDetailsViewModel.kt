package andre.hitchman.com.news.news_article_details.viewmodel

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_article_details.domain.GetNewsArticleDetailsUseCase
import andre.hitchman.com.news.news_article_details.livedatastate.NewsArticleDetailsRequestState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsArticlesDetailsViewModel @Inject constructor(
    private val getNewsArticleDetailsUseCase: GetNewsArticleDetailsUseCase
) : ViewModel() {

    private val _getState = MutableLiveData<NewsArticleDetailsRequestState>()
    val getNewsArticleDetailsRequestState: LiveData<NewsArticleDetailsRequestState> = _getState

    fun loadSelectedNewsArticleDetail(newsArticleId: Int) {
        _getState.value = NewsArticleDetailsRequestState.Loading

        viewModelScope.launch {
            _getState.value =
                when (val result = getNewsArticleDetailsUseCase.execute(newsArticleId)) {
                    is Result.Success -> NewsArticleDetailsRequestState.Data(result.data)
                    is Result.Error -> NewsArticleDetailsRequestState.Error
                }
        }
    }
}