package com.example.newsapp.data.model.response.latestnews


import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.NewsArticle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "abstract")
    var articleabstract: String,
    @Json(name = "byline")
    var byline: String,
    @Json(name = "item_type")
    var itemType: String,
    @Json(name = "multimedia")
    var multimedia: List<Multimedia>,
    @Json(name = "published_date")
    var publishedDate: String,
    @Json(name = "related_urls")
    var relatedUrls: List<RelatedUrl>,
    @Json(name = "section")
    var section: String,
    @Json(name = "source")
    var source: String,
    @Json(name = "subsection")
    var subsection: String,
    @Json(name = "title")
    var title: String,
    @Json(name = "uri")
    var uri: String,
    @Json(name = "url")
    var url: String
){
    fun toNewsArticle(): NewsArticle{
        return NewsArticle(
            articleabstract = articleabstract,
            byline = byline,
            itemType = itemType,
            publishedDate = publishedDate,
            section = section,
            subsection = subsection,
            title = title,
            nyturi = uri,
            htmlurl = url,
            multimedia = multimedia
        )
    }
}