package com.example.newsapp.data.model.response.search


import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.NewsArticle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val TAG = "DOC DATA CLASS"
@JsonClass(generateAdapter = true)
data class Doc(
    @Json(name = "abstract")
    var `abstract`: String?,
    @Json(name = "document_type")
    var documentType: String?,
    @Json(name = "_id")
    var id: String?,
    @Json(name = "lead_paragraph")
    var leadParagraph: String?,
    @Json(name = "multimedia")
    var multimedia: List<Multimedia>?,
    @Json(name = "news_desk")
    var newsDesk: String?,
    @Json(name = "pub_date")
    var pubDate: String?,
    @Json(name = "section_name")
    var sectionName: String?,
    @Json(name = "snippet")
    var snippet: String?,
    @Json(name = "source")
    var source: String?,
    @Json(name = "type_of_material")
    var typeOfMaterial: String?,
    @Json(name = "uri")
    var uri: String?,
    @Json(name = "web_url")
    var webUrl: String?,
    @Json(name = "word_count")
    var wordCount: Int?
){
    fun toNewsArticle(): NewsArticle {
        return NewsArticle(
            articleabstract = abstract ?: "",
            htmlurl = webUrl ?: "",
            title = snippet ?: "",
            multimedia = multimedia ,
            publishedDate = pubDate ?: "",
            itemType = typeOfMaterial ?: "",
            nyturi = uri ?: "",
            section = sectionName ?: "",
            subsection = "",
            byline = ""
        )
    }
}