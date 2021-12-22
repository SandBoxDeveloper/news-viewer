package andre.hitchman.com.news.news_article_details.viewholder

import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.core.viewholder.BaseViewHolder
import andre.hitchman.com.news.news_article_details.databinding.ItemNewsArticleDetailsSectionBodyTextBinding
import android.view.ViewGroup

class DetailedItemBodySectionViewHolder constructor(
    parent: ViewGroup
) : BaseViewHolder<UINewsArticleDetails.SectionBody, ItemNewsArticleDetailsSectionBodyTextBinding>(
    parent,
    ItemNewsArticleDetailsSectionBodyTextBinding::inflate
) {

    override fun bind(item: UINewsArticleDetails.SectionBody) {
        binding.itemNewArticleDetailsSectionBodyTextView.text = item.text
    }
}