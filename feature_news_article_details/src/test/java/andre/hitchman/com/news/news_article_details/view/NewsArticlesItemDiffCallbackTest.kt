package andre.hitchman.com.news.news_article_details.view

import andre.hitchman.com.news.news_article_details.model.UINewsArticleDetails
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NewsArticlesItemDiffCallbackTest {

    private val subject = NewsArticlesItemDiffCallback()

    @Test
    fun `areItemsTheSame() - oldItem are the same As new item`() {
        val oldItem: UINewsArticleDetails = UINewsArticleDetails.Title("Title")
        val newItem: UINewsArticleDetails = UINewsArticleDetails.Title("Title")

        val areItemsTheSame = subject.areItemsTheSame(oldItem, newItem)

        assertThat(areItemsTheSame).isTrue()
    }

    @Test
    fun `areContentsTheSame() - oldItem contents are the same as new item contents`() {
        val oldItem: UINewsArticleDetails = UINewsArticleDetails.Title("Title")
        val newItem: UINewsArticleDetails = UINewsArticleDetails.Title("Title")

        val areContentsTheSame = subject.areContentsTheSame(oldItem, newItem)

        assertThat(areContentsTheSame).isTrue()
    }
}