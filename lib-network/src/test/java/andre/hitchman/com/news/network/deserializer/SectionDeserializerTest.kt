package andre.hitchman.com.news.network.deserializer

import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import andre.hitchman.com.news.testutils.rules.MockKRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import org.junit.Rule
import org.junit.Test
import java.util.*

class SectionDeserializerTest {

    @get:Rule
    val mockKRule = MockKRule()

    @Test
    fun `deserialize() - given section text, then SectionItemSectionText is returned`() {
        val gson = GsonBuilder().registerTypeAdapter(
            NewsArticleItemSectionBodyElement::class.java,
            SectionDeserializer()
        )
            .create()
        val toJson =
            gson.toJson("With 1.34 billion passengers a year, the Tube is an intrinsic part of London life. Any disruptions, however small, to this essential service can cause a ripple effect that is felt not just on the network, but across the city itself.")

        val item = gson.fromJson(toJson, NewsArticleItemSectionBodyElement::class.java)

        assertThat(item).isEqualTo(NewsArticleItemSectionBodyElement.SectionText(sectionText = """With 1.34 billion passengers a year, the Tube is an intrinsic part of London life. Any disruptions, however small, to this essential service can cause a ripple effect that is felt not just on the network, but across the city itself."""))
    }

    @Test
    fun `deserialize() - given image url, then SectionItemImageUrl is returned`() {
        val jsonWithImageUrl: String = readFileAsString(
            this.javaClass,
            "newsarticles/news_articles_section_with_image_url_only.json"
        )
        val gson = GsonBuilder().registerTypeAdapter(
            NewsArticleItemSectionBodyElement::class.java,
            SectionDeserializer()
        )
            .create()

        val item = gson.fromJson(jsonWithImageUrl, NewsArticleItemSectionBodyElement::class.java)

        assertThat(item).isEqualTo(NewsArticleItemSectionBodyElement.ImageUrl(url = """https://raw.githubusercontent.com/theappbusiness/engineering-challenge/main/endpoints/v2/images/diagram_fs_valuechain1-2x.png"""))
    }

    @Test(expected = JsonParseException::class)
    fun `deserialize() - given null, then exception is thrown`() {
        val json: String =
            readFileAsString(this.javaClass, "newsarticles/news_articles_empty_array.json")
        val gson = GsonBuilder().registerTypeAdapter(
            NewsArticleItemSectionBodyElement::class.java,
            SectionDeserializer()
        )
            .create()

        gson.fromJson(json, NewsArticleItemSectionBodyElement::class.java)
    }

    private fun readFileAsString(clazz: Class<*>, name: String): String {
        val inputStream = clazz.classLoader?.getResourceAsStream(name)
            ?: throw IllegalArgumentException("File not found: $name")
        val scanner = Scanner(inputStream).useDelimiter("\\A")
        return if (scanner.hasNext()) scanner.next() else ""
    }
}