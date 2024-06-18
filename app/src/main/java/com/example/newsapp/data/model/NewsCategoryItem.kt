package com.example.newsapp.data.model

import androidx.compose.runtime.Immutable
import com.example.newsapp.data.repositories.local.entities.NewsCategoryEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID


@Immutable
@JsonClass(generateAdapter = true)
data class NewsCategoryItem(
    val id: Int?,
    @Json(name = "section")
    val name: String,
){
    fun toNewsCategoryEntity():NewsCategoryEntity{
        val myUUID = UUID.randomUUID()
        return NewsCategoryEntity(id = myUUID, name = name)
    }
}
