package com.example.newsapp.data.model.response.category


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    @Json(name = "copyright")
    var copyright: String,
    @Json(name = "num_results")
    var numResults: Int,
    @Json(name = "results")
    var results: List<Result>,
    @Json(name = "status")
    var status: String
)