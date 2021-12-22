package andre.hitchman.com.news.news_article_details.view

import andre.hitchman.com.news.base.image.GlideImageLoader
import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import andre.hitchman.com.news.news_article_details.viewholder.DetailedItemBodySectionViewHolder
import andre.hitchman.com.news.news_article_details.viewholder.DetailedItemNewsArticleHeroImageViewHolder
import andre.hitchman.com.news.news_article_details.viewholder.DetailedItemImageSectionViewHolder
import andre.hitchman.com.news.news_article_details.viewholder.DetailedItemTitleViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class NewsArticleDetailsAdapter @Inject constructor(
    private val imageLoader: GlideImageLoader
) : ListAdapter<UINewsArticleDetails, RecyclerView.ViewHolder>(NewsArticlesItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            UINewsArticleDetails.ViewType.TITLE ->
                DetailedItemTitleViewHolder(parent)
            UINewsArticleDetails.ViewType.HERO_IMAGE ->
                DetailedItemNewsArticleHeroImageViewHolder(imageLoader, parent)
            UINewsArticleDetails.ViewType.SECTION_BODY ->
                DetailedItemBodySectionViewHolder(parent)
            UINewsArticleDetails.ViewType.SECTION_IMAGE ->
                DetailedItemImageSectionViewHolder(imageLoader, parent)
            else -> throw IllegalArgumentException("Unknown ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailedItemTitleViewHolder -> holder.bind(getItem(position) as UINewsArticleDetails.Title)
            is DetailedItemNewsArticleHeroImageViewHolder -> holder.bind(getItem(position) as UINewsArticleDetails.HeroImage)
            is DetailedItemBodySectionViewHolder -> holder.bind(getItem(position) as UINewsArticleDetails.SectionBody)
            is DetailedItemImageSectionViewHolder -> holder.bind(getItem(position) as UINewsArticleDetails.SectionImage)
        }
    }

    fun updateListOfItems(UINewsArticlesListDetails: List<UINewsArticleDetails>) {
        submitList(UINewsArticlesListDetails)
    }
}

class NewsArticlesItemDiffCallback : DiffUtil.ItemCallback<UINewsArticleDetails>() {

    override fun areItemsTheSame(
        oldItem: UINewsArticleDetails,
        newItem: UINewsArticleDetails
    ): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(
        oldItem: UINewsArticleDetails,
        newItem: UINewsArticleDetails
    ): Boolean {
        return oldItem == newItem
    }
}