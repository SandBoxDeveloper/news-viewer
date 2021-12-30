package andre.hitchman.com.news.news_list.view

import andre.hitchman.com.news.core.NewsDetailsNavigationRoute
import andre.hitchman.com.news.news_list.R
import andre.hitchman.com.news.news_list.databinding.NewsArticlesListFragmentBinding
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListEventState
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListNavigationState
import andre.hitchman.com.news.news_list.livedatastate.NewsArticlesListRequestState
import andre.hitchman.com.news.news_list.model.UICaseStudyList
import andre.hitchman.com.news.news_list.viewmodel.NewsListViewModel
import andre.hitchman.com.news.news_list.viewmodel.NewsListViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : Fragment(), NewsArticleItemClickLister {

    @Inject
    lateinit var viewModelFactory: NewsListViewModelFactory

    @Inject
    lateinit var listAdapter: NewsArticlesAdapter

    @Inject
    lateinit var detailsNavigationRoute: NewsDetailsNavigationRoute

    private var newsArticlesListFragmentBinding: NewsArticlesListFragmentBinding? = null

    private val viewModel by viewModels<NewsListViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NewsArticlesListFragmentBinding.inflate(inflater, container, false)
        newsArticlesListFragmentBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsArticlesListFragmentBinding?.newsArticlesListRecyclerView?.adapter = listAdapter
        newsArticlesListFragmentBinding?.retryButton?.setOnClickListener { viewModel.requestApi() }
        listAdapter.onNewsArticleItemClickLister = this
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsArticlesListFragmentBinding = null
    }

    private fun observeState() {
        viewModel.apply {
            getNewsArticlesStateNewsArticlesList.observe(
                viewLifecycleOwner,
                { state -> handleState(state) })
            getNewsArticlesListNavigationState.observe(
                viewLifecycleOwner,
                { state -> handleNavigation(state) })
            getNewsArticlesListEventState.observe(
                viewLifecycleOwner,
                { state -> handleEventState(state) })
        }
    }

    private fun handleState(state: NewsArticlesListRequestState?) {
        when (state) {
            is NewsArticlesListRequestState.Loading -> showLoadingState()
            is NewsArticlesListRequestState.Completed -> showCompletedState(state)
            is NewsArticlesListRequestState.Error -> showErrorState()
        }
    }

    private fun handleNavigation(state: NewsArticlesListNavigationState?) {
        when (state) {
            is NewsArticlesListNavigationState.NavigateToNewsArticleDetailedView -> {
                detailsNavigationRoute.toDetailsView(state.newsArticleItemId, findNavController())
            }
        }
    }

    private fun handleEventState(state: NewsArticlesListEventState?) {
        when (state) {
            is NewsArticlesListEventState.NetworkStateLost -> Snackbar.make(
                requireView(),
                getString(R.string.snackbar_no_internet_connection_text),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.snackbar_dismiss_text)) {}.show()

            is NewsArticlesListEventState.NetworkUnavailable -> Snackbar.make(
                requireView(),
                getString(R.string.snackbar_viewing_offline_mode_text),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.snackbar_dismiss_text)) {}.show()
        }
    }

    private fun showLoadingState() {
        newsArticlesListFragmentBinding?.apply {
            loadingProgressBar.visibility = View.VISIBLE
            newsArticlesListRecyclerView.visibility = View.GONE
            fullScreenErrorText.visibility = View.GONE
            noNewsArticlesMessage.visibility = View.GONE
        }
    }

    private fun showCompletedState(state: NewsArticlesListRequestState.Completed) {
        newsArticlesListFragmentBinding?.apply {
            newsArticlesListRecyclerView.visibility = View.VISIBLE
            loadingProgressBar.visibility = View.GONE
            fullScreenErrorText.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
        displayCaseStudies(state.caseStudy)
    }

    private fun showErrorState() {
        newsArticlesListFragmentBinding?.apply {
            loadingProgressBar.visibility = View.GONE
            newsArticlesListRecyclerView.visibility = View.GONE
            fullScreenErrorText.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            retryButton.setOnClickListener {
                viewModel.requestApi()
            }
        }
    }

    private fun displayCaseStudies(state: UICaseStudyList) {
        when (state) {
            UICaseStudyList.NoCaseStudyList -> {
                newsArticlesListFragmentBinding?.noNewsArticlesMessage?.visibility = View.VISIBLE
            }
            is UICaseStudyList.WithCaseStudyList -> {
                newsArticlesListFragmentBinding?.noNewsArticlesMessage?.visibility = View.GONE
                listAdapter.updateListOfItems(state.newsArticle)
            }
        }
    }

    override fun onNewsArticleItemClicked(newsArticleItemId: Int) {
        viewModel.navigateToDetailedView(newsArticleItemId)
    }

    override fun onStop() {
        super.onStop()
        viewModel.unregisterMonitoringOfNetworkState()
    }

    override fun onStart() {
        super.onStart()
        viewModel.monitorNetworkState()
        viewModel.checkIfNetworkIsAvailable()
    }
}