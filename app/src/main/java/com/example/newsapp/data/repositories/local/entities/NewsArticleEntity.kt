package com.example.newsapp.data.repositories.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.utils.Constants

@Entity(
    tableName = Constants.TABLE_NEWS_ARTICLES,
)
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var articleabstract: String,
    var byline: String,
    var itemType: String,
    var publishedDate: String,
    var section: String,
    var subsection: String,
    var title: String,
    var nyturi: String,
    var htmlurl: String
)
