package com.example.newsapp.data.model.response.latestnews


import com.example.newsapp.data.model.NewsArticle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class LatestNewsResponse(
    @Json(name = "copyright")
    var copyright: String,
    @Json(name = "num_results")
    var numResults: Int,
    @Json(name = "results")
    var results: List<NewsArticle>,
    @Json(name = "status")
    var status: String
)