package andre.hitchman.com.news.news_list.viewmodel

import andre.hitchman.com.news.base.domain.Result
import andre.hitchman.com.news.news_list.domain.GetNewsArticlesUseCase
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListEventState
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListNavigationState
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListRequestState
import andre.hitchman.com.news.news_list.model.UICaseStudyList
import andre.hitchman.com.news.core.livedatastate.SingleLiveEvent
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.network.NetworkChangedEvent
import andre.hitchman.com.news.network.NetworkChecker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsArticlesUseCase: GetNewsArticlesUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel(), NetworkChangedEvent {

    private val _getNewsArticlesState = MutableLiveData<NewsArticlesListRequestState>()
    val getNewsArticlesStateNewsArticlesList: LiveData<NewsArticlesListRequestState> =
        _getNewsArticlesState

    private val _getNavigationState =
        SingleLiveEvent<NewsArticlesListNavigationState>()
    val getNewsArticlesListNavigationState: SingleLiveEvent<NewsArticlesListNavigationState> =
        _getNavigationState

    private val _getEventState =
        SingleLiveEvent<NewsArticlesListEventState>()
    val getNewsArticlesListEventState: SingleLiveEvent<NewsArticlesListEventState> = _getEventState

    init {
        requestApi()
    }

    override fun onNetworkLost() {
        _getEventState.postValue(NewsArticlesListEventState.NetworkStateLost)
    }

    fun requestApi() {
        _getNewsArticlesState.value = NewsArticlesListRequestState.Loading

        viewModelScope.launch {
            _getNewsArticlesState.value = when (val result = getNewsArticlesUseCase.execute()) {
                is Result.Success -> displayListOfNewsArticles(result.data)
                is Result.Error -> NewsArticlesListRequestState.Error
            }
        }
    }

    fun navigateToDetailedView(caseStudyItemId: Int) {
        _getNavigationState.value =
            NewsArticlesListNavigationState.NavigateToNewsArticleDetailedView(caseStudyItemId)
    }

    fun monitorNetworkState() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            networkChecker.setupEvent(this)
            networkChecker.registerNetworkCallback()
        }
    }

    fun unregisterMonitoringOfNetworkState() {
        networkChecker.unregisterNetworkCallback()
    }

    fun checkIfNetworkIsAvailable() {
        if (!networkChecker.isNetworkAvailable()) {
            _getEventState.postValue(NewsArticlesListEventState.NetworkUnavailable)
        }
    }

    private fun displayListOfNewsArticles(data: List<NewsArticleItem>): NewsArticlesListRequestState {
        return if (data.isEmpty()) {
            NewsArticlesListRequestState.Completed(UICaseStudyList.NoCaseStudyList)
        } else {
            NewsArticlesListRequestState.Completed(UICaseStudyList.WithCaseStudyList(data))
        }
    }
}