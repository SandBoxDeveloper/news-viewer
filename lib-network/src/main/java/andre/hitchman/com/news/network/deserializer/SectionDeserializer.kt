package andre.hitchman.com.news.network.deserializer

import andre.hitchman.com.news.model.NewsArticleItemSectionBodyElement
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class SectionDeserializer : JsonDeserializer<NewsArticleItemSectionBodyElement> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NewsArticleItemSectionBodyElement {

        if (json != null) {
            return when {
                json.isJsonPrimitive -> {
                    NewsArticleItemSectionBodyElement.SectionText(json.asJsonPrimitive.asString)
                }
                json.isJsonObject -> {
                    val url = json.asJsonObject.get("image_url")
                    NewsArticleItemSectionBodyElement.ImageUrl(url.asJsonPrimitive.asString)
                }
                else -> throw JsonParseException("cannot parse json")
            }
        } else {
            throw JsonParseException("cannot parse json")
        }
    }
}
