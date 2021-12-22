package andre.hitchman.com.news.model

data class NewsArticleItem constructor(
    val newsArticleId: Int,
    val teaserText: String?,
    val newsArticleTitle: String?,
    val heroImage: String?,
    val sectionsList: List<NewsArticleItemSection>? = emptyList()
) {

    /**
     * Creates a builder for an NewsArticleItem.
     */
    class NewsArticleItemBuilder {
        //builder sets the default values
        private var id: Int? = null
        private var teaser: String? = null
        private var title: String? = null
        private var heroImageUrl: String? = null
        private var sectionsList: List<NewsArticleItemSection> = emptyList()

        /**
         * Set the id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun newsArticleId(id: Int): NewsArticleItemBuilder {
            this.id = id
            return this
        }

        /**
         * Set the teaser text.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun teaserText(text: String?): NewsArticleItemBuilder {
            teaser = text
            return this
        }

        /**
         * Set the title.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun newsArticleTitle(title: String?): NewsArticleItemBuilder {
            this.title = title
            return this
        }

        /**
         * Set the url for the hero image.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun heroImage(url: String?): NewsArticleItemBuilder {
            heroImageUrl = url
            return this
        }

        /**
         * Set the list of sections.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun sectionsList(list: List<NewsArticleItemSection>): NewsArticleItemBuilder {
            sectionsList = list
            return this
        }

        /**
         * Creates an NewsArticleItem with the arguments supplied to this builder.
         */
        fun build(): NewsArticleItem = NewsArticleItem(
            newsArticleId = id ?: throw IllegalArgumentException("missing required news article id"),
            teaserText = teaser,
            newsArticleTitle = title,
            heroImage = heroImageUrl,
            sectionsList = this.sectionsList
        )
    }
}
