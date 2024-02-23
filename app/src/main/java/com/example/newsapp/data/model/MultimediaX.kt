package com.example.newsapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultimediaX(
    @Json(name = "caption")
    var caption: Any?,
    @Json(name = "credit")
    var credit: Any?,
    @Json(name = "crop_name")
    var cropName: String,
    @Json(name = "height")
    var height: Int,
    @Json(name = "legacy")
    var legacy: Legacy?,
    @Json(name = "rank")
    var rank: Int,
    @Json(name = "subType")
    var subType: String,
    @Json(name = "subtype")
    var subtype: String,
    @Json(name = "type")
    var type: String,
    @Json(name = "url")
    var url: String,
    @Json(name = "width")
    var width: Int
)