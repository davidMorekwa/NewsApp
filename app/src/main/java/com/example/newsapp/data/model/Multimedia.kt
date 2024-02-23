package com.example.newsapp.data.model

import com.example.newsapp.data.repositories.local.entities.MultimediaEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Multimedia(
    @Json(name = "caption")
    var caption: String?,
    @Json(name = "copyright")
    var copyright: String?,
    @Json(name = "format")
    var format: String?,
    @Json(name = "height")
    var height: Int?,
    @Json(name = "subtype")
    var subtype: String?,
    @Json(name = "type")
    var type: String?,
    @Json(name = "url")
    var url: String?,
    @Json(name = "width")
    var width: Int?
){
    fun toMultimediaEntity(articleid: Int): MultimediaEntity{
        return MultimediaEntity(
            id = null,
            caption = caption,
            articleId = null,
            copyright = copyright,
            format = format,
            height = height,
            subtype = subtype,
            type = type,
            url = url,
            width = width
        )
    }
}