package andre.hitchman.com.news.news_article_details.view

import andre.hitchman.com.news.news_article_details.databinding.NewsArticleDetailedFragmentBinding
import andre.hitchman.com.news.news_article_details.livedatastate.NewsArticleDetailsRequestState
import andre.hitchman.com.news.news_article_details.mapper.NewsArticleItemListMapper
import andre.hitchman.com.news.news_article_details.model.NewsArticleDetails
import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.news_article_details.viewmodel.NewsArticlesDetailsViewModel
import andre.hitchman.com.news.news_article_details.viewmodel.NewsArticlesDetailsViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsArticleDetailsFragment : Fragment() {

    @Inject
    lateinit var detailsAdapter: NewsArticleDetailsAdapter

    @Inject
    lateinit var viewModelFactory: NewsArticlesDetailsViewModelFactory

    @Inject
    lateinit var newsArticleItemListMapper: NewsArticleItemListMapper

    private val viewModel by viewModels<NewsArticlesDetailsViewModel> { viewModelFactory }

    private var detailedNewsArticleFragmentBinding: NewsArticleDetailedFragmentBinding? = null
    private val args: NewsArticleDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NewsArticleDetailedFragmentBinding.inflate(inflater, container, false)
        detailedNewsArticleFragmentBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailedNewsArticleFragmentBinding?.newsArticleDetailsRecyclerView?.adapter = detailsAdapter
        loadNewsArticleDetailsFromDatabase()
        observeState()
    }

    private fun loadNewsArticleDetailsFromDatabase() {
        viewModel.loadSelectedNewsArticleDetail(args.newsArticleId)
    }

    private fun observeState() {
        viewModel.getNewsArticleDetailsRequestState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NewsArticleDetailsRequestState.Loading -> showLoadingState()
                is NewsArticleDetailsRequestState.Data -> showData(state)
                is NewsArticleDetailsRequestState.NoData -> showNoDataState()
                is NewsArticleDetailsRequestState.Error -> showErrorState()
            }
        })
    }

    private fun showLoadingState() {
        detailedNewsArticleFragmentBinding?.apply {
            newsArticleDetailsLoadingProgressBar.visibility = View.VISIBLE
            newsArticleDetailsRecyclerView.visibility = View.GONE
            fullScreenNoDataView.visibility = View.GONE
            fullScreenErrorText.visibility = View.GONE
        }
    }

    private fun showData(state: NewsArticleDetailsRequestState.Data) {
        detailedNewsArticleFragmentBinding?.apply {
            newsArticleDetailsLoadingProgressBar.visibility = View.GONE
            fullScreenNoDataView.visibility = View.GONE
            fullScreenErrorText.visibility = View.GONE
            newsArticleDetailsRecyclerView.visibility = View.VISIBLE
        }
        (activity as AppCompatActivity).supportActionBar?.title = state.newsArticleDetails.title
        detailsAdapter.updateListOfItems(buildList(state.newsArticleDetails))
    }

    private fun showNoDataState() {
        detailedNewsArticleFragmentBinding?.apply {
            newsArticleDetailsLoadingProgressBar.visibility = View.GONE
            newsArticleDetailsRecyclerView.visibility = View.GONE
            fullScreenErrorText.visibility = View.GONE
            fullScreenNoDataView.visibility = View.VISIBLE
        }
    }

    private fun showErrorState() {
        detailedNewsArticleFragmentBinding?.apply {
            newsArticleDetailsLoadingProgressBar.visibility = View.GONE
            newsArticleDetailsRecyclerView.visibility = View.GONE
            fullScreenNoDataView.visibility = View.GONE
            fullScreenErrorText.visibility = View.VISIBLE
        }
    }

    private fun buildList(
        data: NewsArticleDetails
    ): List<UINewsArticleDetails> {
        val sectionsList = mutableListOf<UINewsArticleDetails>()
        val list = newsArticleItemListMapper.fromListToUiData(data)
        sectionsList.addAll(list)
        return sectionsList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailedNewsArticleFragmentBinding = null
    }
}