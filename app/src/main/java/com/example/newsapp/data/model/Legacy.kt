package com.example.newsapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Legacy(
    @Json(name = "thumbnail")
    var thumbnail: String?,
    @Json(name = "thumbnailheight")
    var thumbnailheight: Int?,
    @Json(name = "thumbnailwidth")
    var thumbnailwidth: Int?,
    @Json(name = "wide")
    var wide: String?,
    @Json(name = "wideheight")
    var wideheight: Int?,
    @Json(name = "widewidth")
    var widewidth: Int?,
    @Json(name = "xlarge")
    var xlarge: String?,
    @Json(name = "xlargeheight")
    var xlargeheight: Int?,
    @Json(name = "xlargewidth")
    var xlargewidth: Int?
)