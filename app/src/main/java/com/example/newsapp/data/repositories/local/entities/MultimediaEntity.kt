package com.example.newsapp.data.repositories.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.example.newsapp.data.utils.Constants

@Entity(
    tableName = Constants.TABLE_MULTIMEDIA
)
data class MultimediaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var caption: String?,
    var articleId: Int?,
    var copyright: String?,
    var format: String?,
    var height: Int?,
    var subtype: String?,
    var type: String?,
    var url: String?,
    var width: Int?
)