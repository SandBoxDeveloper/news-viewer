package andre.hitchman.com.news.news_list.viewholder

import andre.hitchman.com.news.base.image.ImageLoader
import andre.hitchman.com.news.news_list.view.NewsArticleItemClickLister
import andre.hitchman.com.news.core.viewholder.BaseViewHolder
import andre.hitchman.com.news.model.NewsArticleItem
import andre.hitchman.com.news.news_list.databinding.ItemNewsArticleListItemBinding
import android.view.ViewGroup

class NewsArticleItemViewHolder(
    private val imageLoader: ImageLoader,
    private val onNewsArticleItemClickLister: NewsArticleItemClickLister?,
    parent: ViewGroup
) : BaseViewHolder<NewsArticleItem, ItemNewsArticleListItemBinding>(
    parent,
    ItemNewsArticleListItemBinding::inflate
) {

    override fun bind(item: NewsArticleItem) {
        binding.itemNewsArticleTitle.text = item.teaserText
        imageLoader.loadImage(binding.itemNewsArticleImage, item.heroImage)
        itemView.setOnClickListener {
            onNewsArticleItemClickLister?.onNewsArticleItemClicked(item.newsArticleId)
        }
    }
}