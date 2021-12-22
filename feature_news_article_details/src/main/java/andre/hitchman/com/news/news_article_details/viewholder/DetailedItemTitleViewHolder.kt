package andre.hitchman.com.news.news_article_details.viewholder

import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.core.viewholder.BaseViewHolder
import andre.hitchman.com.news.news_article_details.databinding.ItemNewsArticleDetailsSectionTitleBinding
import android.view.ViewGroup

class DetailedItemTitleViewHolder constructor(
    parent: ViewGroup
) : BaseViewHolder<UINewsArticleDetails.Title, ItemNewsArticleDetailsSectionTitleBinding>(
    parent,
    ItemNewsArticleDetailsSectionTitleBinding::inflate
) {

    override fun bind(item: UINewsArticleDetails.Title) {
        binding.itemNewsArticleDetailsSectionTitleView.text = item.title
    }
}