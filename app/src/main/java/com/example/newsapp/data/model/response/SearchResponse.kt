package com.example.newsapp.data.model.response


import com.example.newsapp.data.model.Response
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "copyright")
    var copyright: String,
    @Json(name = "response")
    var response: Response,
    @Json(name = "status")
    var status: String
)