package com.example.newsapp.data.model


import androidx.compose.runtime.Immutable
import com.example.newsapp.data.repositories.local_data.entities.NewsArticleEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

@Immutable
@JsonClass(generateAdapter = true)
data class NewsArticle(
    @Json(name = "abstract")
    var articleabstract: String,
    @Json(name = "byline")
    var byline: String,
    @Json(name = "item_type")
    var itemType: String,
    @Json(name = "multimedia")
    var multimedia: List<Multimedia>?,
    @Json(name = "published_date")
    var publishedDate: String,
    @Json(name = "section")
    var section: String,
    @Json(name = "subsection")
    var subsection: String,
    @Json(name = "title")
    var title: String,
    @Json(name = "uri")
    var nyturi: String,
    @Json(name = "url")
    var htmlurl: String
){
    fun toNewsArticleEntity():NewsArticleEntity{
        val myUUID = UUID.randomUUID()
        return NewsArticleEntity(
            id = myUUID,
            articleabstract = articleabstract,
            byline = byline,
            itemType = itemType,
            publishedDate = publishedDate,
            section = section,
            subsection = subsection,
            title = title,
            nyturi = nyturi,
            htmlurl = htmlurl
        )
    }
}