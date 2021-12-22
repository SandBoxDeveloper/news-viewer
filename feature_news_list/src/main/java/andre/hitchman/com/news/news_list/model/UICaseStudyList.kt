package andre.hitchman.com.news.news_list.model

import andre.hitchman.com.news.model.NewsArticleItem

sealed class UICaseStudyList {

    object NoCaseStudyList : UICaseStudyList()

    data class WithCaseStudyList(val newsArticle: List<NewsArticleItem>) : UICaseStudyList()
}