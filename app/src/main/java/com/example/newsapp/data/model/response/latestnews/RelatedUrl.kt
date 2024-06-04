package com.example.newsapp.data.model.response.latestnews


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedUrl(
    @Json(name = "suggested_link_text")
    var suggestedLinkText: String,
    @Json(name = "url")
    var url: String
)