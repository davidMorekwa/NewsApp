package com.example.newsapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "docs")
    var docs: List<Doc>
)