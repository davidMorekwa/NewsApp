package com.example.newsapp.data.model

import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Immutable
@JsonClass(generateAdapter = true)
data class NewsCategoryItem(
    val id: Int?,
    @Json(name = "section")
    val name: String,
)
