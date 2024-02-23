package com.example.newsapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Doc(
    @Json(name = "abstract")
    var `abstract`: String,
    @Json(name = "document_type")
    var documentType: String,
    @Json(name = "_id")
    var id: String,
    @Json(name = "lead_paragraph")
    var leadParagraph: String,
    @Json(name = "multimedia")
    var multimedia: List<MultimediaX>,
    @Json(name = "news_desk")
    var newsDesk: String,
    @Json(name = "pub_date")
    var pubDate: String,
    @Json(name = "section_name")
    var sectionName: String,
    @Json(name = "snippet")
    var snippet: String,
    @Json(name = "source")
    var source: String,
    @Json(name = "type_of_material")
    var typeOfMaterial: String,
    @Json(name = "uri")
    var uri: String,
    @Json(name = "web_url")
    var webUrl: String,
    @Json(name = "word_count")
    var wordCount: Int
)