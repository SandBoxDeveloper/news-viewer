package andre.hitchman.com.news.core

import androidx.navigation.NavController

interface NewsDetailsNavigationRoute {
    fun toDetailsView(newsArticleItemId: Int, navController: NavController)
}