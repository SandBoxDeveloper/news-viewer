package andre.hitchman.com.news.news_list.livedatastate

import andre.hitchman.com.news.news_list.model.UICaseStudyList

sealed class NewsArticlesListRequestState {

    object Loading : NewsArticlesListRequestState()
    data class Completed(val caseStudy: UICaseStudyList) : NewsArticlesListRequestState()
    object Error : NewsArticlesListRequestState()
}