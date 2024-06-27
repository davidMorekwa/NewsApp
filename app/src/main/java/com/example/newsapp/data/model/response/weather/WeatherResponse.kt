package com.example.newsapp.data.model.response.weather

import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
data class WeatherResponse (
    @Json(name="weather")
    val weather: List<Weather>,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "main")
    val main: Main
)