package andre.hitchman.com.news.news_list.viewmodel

import andre.hitchman.com.news.news_list.domain.GetNewsArticlesUseCase
import andre.hitchman.com.news.network.NetworkChecker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class NewsListViewModelFactory @Inject constructor(
    private val getNewsArticlesUseCase: GetNewsArticlesUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(
                getNewsArticlesUseCase,
                networkChecker
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}