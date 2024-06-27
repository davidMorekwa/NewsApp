package com.example.newsapp.data.model.response.category


import com.example.newsapp.data.model.NewsCategoryItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "display_name")
    var section: String
){
    fun toNewsCategoryItem(id: Int):NewsCategoryItem{
        return NewsCategoryItem(id = id, name = section)
    }
}