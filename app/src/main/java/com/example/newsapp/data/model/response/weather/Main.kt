package com.example.newsapp.data.model.response.weather

import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double
)
