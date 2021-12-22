package andre.hitchman.com.news.news_article_details.viewholder

import andre.hitchman.com.news.base.image.GlideImageLoader
import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.core.viewholder.BaseViewHolder
import andre.hitchman.com.news.news_article_details.databinding.ItemNewsArticleDetailsSectionUrlsBinding
import android.view.ViewGroup

class DetailedItemImageSectionViewHolder constructor(
    private val imageLoader: GlideImageLoader,
    parent: ViewGroup
) : BaseViewHolder<UINewsArticleDetails.SectionImage, ItemNewsArticleDetailsSectionUrlsBinding>(
    parent,
    ItemNewsArticleDetailsSectionUrlsBinding::inflate
) {

    override fun bind(item: UINewsArticleDetails.SectionImage) {
        imageLoader.loadImage(binding.itemNewArticleDetailsSectionUrlsView, item.url)
    }
}