package andre.hitchman.com.news.viewer

import andre.hitchman.com.news.database.newsarticles.NewsArticleDao
import andre.hitchman.com.news.database.newsarticles.NewsArticlesDatabase
import andre.hitchman.com.news.database.model.NewsArticle
import andre.hitchman.com.news.database.model.SectionItems
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsArticleDatabaseTest {

    companion object {
        const val id: Int = 1
        const val teaserText: String = "the world according to Kotlin"
        const val titleText: String = "Kotlin World"
        const val heroImageUrl: String = "www.dummyurl.com"
    }

    private lateinit var newsArticlesDatabase: NewsArticlesDatabase
    private lateinit var newsArticleDao: NewsArticleDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsArticlesDatabase =
            Room.inMemoryDatabaseBuilder(context, NewsArticlesDatabase::class.java).build()
        newsArticleDao = newsArticlesDatabase.newsArticleDao
    }

    @After
    fun tearDown() {
        newsArticlesDatabase.close()
    }

    @Test
    fun givenSectionItemsTableIsPopulatedWithData_whenGettingSectionItemsBasedOnId_thenSectionItemIsReturned() =
        runBlocking {
            val sectionItems = buildSectionItemsList()
            newsArticleDao.insertAll(newsArticles = listOf(), sectionItems = sectionItems)

            val result = newsArticleDao.getSectionItems(1)

            assertThat(result).isEqualTo(sectionItems)
        }

    @Test
    fun givenNewsArticleTableIsPopulated_whenGettingNewsArticleBasedOnId_thenNewsArticleIsReturned() =
        runBlocking {
            val caseStudiesList = buildNewsArticleList()
            newsArticleDao.insertAll(newsArticles = caseStudiesList, sectionItems = listOf())

            val result = newsArticleDao.getNewsArticle(1)

            assertThat(result).isEqualTo(caseStudiesList)
        }

    @Test
    fun givenNewsArticleTableIsPopulated_whenGettingAllNewsArticles_thenListOfNewsArticleIsReturned() =
        runBlocking {
            val caseStudiesList = buildNewsArticleList()
            newsArticleDao.insertAll(newsArticles = caseStudiesList, sectionItems = listOf())

            val result = newsArticleDao.getAllNewsArticles()

            assertThat(result).isEqualTo(caseStudiesList)
        }

    @Test
    fun givenNewsArticleTableIsPopulated_whenDeletingAllNewsArticles_thenEmptyListOfNewsArticleIsReturned() =
        runBlocking {
            val newsArticlesList = buildNewsArticleList()
            newsArticleDao.insertAll(newsArticles = newsArticlesList, sectionItems = listOf())

            newsArticleDao.deleteAllNewsArticleData()

            val result = newsArticleDao.getAllNewsArticles()
            assertThat(result).isEqualTo(emptyList<NewsArticle>())
        }

    @Test
    fun givenSectionItemsTableIsPopulated_whenDeletingAllSectionItems_thenEmptyListOfSectionItemsIsReturned() =
        runBlocking {
            val sectionItems = buildSectionItemsList()
            newsArticleDao.insertAll(newsArticles = emptyList(), sectionItems = sectionItems)

            newsArticleDao.deleteAllSectionItems()

            val result = newsArticleDao.getSectionItems(1)
            assertThat(result).isEqualTo(emptyList<NewsArticle>())
        }

    private fun buildNewsArticleList(): List<NewsArticle> {
        return listOf(
            NewsArticle(
                newsArticleId = id,
                title = titleText,
                teaser = teaserText,
                heroImage = heroImageUrl
            )
        )
    }

    private fun buildSectionItemsList(): List<SectionItems> {
        return listOf(
            SectionItems(
                id = 1,
                newsArticleId = 1,
                sectionText = "yo",
                sectionUrl = "https://raw.githubusercontent.com/theappbusiness/engineering-challenge/main/endpoints/v2/images/decelerator_tube-2x.jpg",
                newsArticleSectionTitle = "section title"
            )
        )
    }
}