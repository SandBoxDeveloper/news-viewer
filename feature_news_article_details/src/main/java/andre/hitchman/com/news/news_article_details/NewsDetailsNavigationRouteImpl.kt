package andre.hitchman.com.news.news_article_details

import andre.hitchman.com.news.core.NewsDetailsNavigationRoute
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import javax.inject.Inject

class NewsDetailsNavigationRouteImpl @Inject constructor() : NewsDetailsNavigationRoute {

    override fun toDetailsView(newsArticleItemId: Int, navController: NavController) {
        navController.navigate(
            R.id.feature_news_details_navigation,
            bundleOf("newsArticleId" to newsArticleItemId)
        )
    }
}