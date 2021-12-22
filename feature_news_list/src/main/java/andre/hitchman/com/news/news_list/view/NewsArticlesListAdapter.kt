package andre.hitchman.com.news.news_list.view

import andre.hitchman.com.news.base.image.ImageLoader
import andre.hitchman.com.news.news_list.viewholder.NewsArticleItemViewHolder
import andre.hitchman.com.news.core.viewholder.BaseViewHolder
import andre.hitchman.com.news.model.NewsArticleItem
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import javax.inject.Inject

interface NewsArticleItemClickLister {
    fun onNewsArticleItemClicked(newsArticleItemId: Int)
}

class NewsArticlesAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : ListAdapter<NewsArticleItem, BaseViewHolder<NewsArticleItem, *>>(
    NewsArticlesListItemDiffCallback()
) {

    var onNewsArticleItemClickLister: NewsArticleItemClickLister? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<NewsArticleItem, *> {
        return when (viewType) {
            ViewType.TEASER_TEXT -> NewsArticleItemViewHolder(
                imageLoader,
                onNewsArticleItemClickLister,
                parent
            )
            else -> throw IllegalArgumentException("Unknown ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<NewsArticleItem, *>,
        position: Int
    ) {
        when (holder) {
            is NewsArticleItemViewHolder -> holder.bind(getItem(position))
        }
    }

    fun updateListOfItems(newsArticlesList: List<NewsArticleItem>) {
        submitList(newsArticlesList)
    }
}

object ViewType {
    const val TEASER_TEXT = 0
}

class NewsArticlesListItemDiffCallback : DiffUtil.ItemCallback<NewsArticleItem>() {

    override fun areItemsTheSame(oldItem: NewsArticleItem, newItem: NewsArticleItem): Boolean {
        return oldItem.newsArticleId == newItem.newsArticleId
    }

    override fun areContentsTheSame(oldItem: NewsArticleItem, newItem: NewsArticleItem): Boolean {
        return oldItem.equals(newItem)
    }
}