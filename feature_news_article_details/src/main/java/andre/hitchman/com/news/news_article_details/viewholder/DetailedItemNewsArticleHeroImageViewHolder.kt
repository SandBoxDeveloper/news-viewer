package andre.hitchman.com.news.news_article_details.viewholder

import andre.hitchman.com.news.base.image.GlideImageLoader
import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.core.viewholder.BaseViewHolder
import andre.hitchman.com.news.news_article_details.databinding.ItemNewsArticleDetailsSectionImageBinding
import android.view.ViewGroup

class DetailedItemNewsArticleHeroImageViewHolder constructor(
    private val imageLoader: GlideImageLoader,
    parent: ViewGroup
) : BaseViewHolder<UINewsArticleDetails.HeroImage, ItemNewsArticleDetailsSectionImageBinding>(
    parent,
    ItemNewsArticleDetailsSectionImageBinding::inflate
) {

    override fun bind(item: UINewsArticleDetails.HeroImage) {
        imageLoader.loadImage(
            binding.itemNewsArticleDetailsSectionImageView,
            item.imageUrl
        )
    }
}