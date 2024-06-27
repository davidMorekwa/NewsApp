package com.example.newsapp.data.repositories.local_data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.utils.Constants
import java.util.UUID

@Entity(
    tableName = Constants.TABLE_NEWS_ARTICLES,
)
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = false)
    var id: UUID,
    var articleabstract: String,
    var byline: String,
    var itemType: String,
    var publishedDate: String,
    var section: String,
    var subsection: String,
    var title: String,
    var nyturi: String,
    var htmlurl: String,
    var isFavorite: Boolean = false,
    var isBookmark: Boolean = false
)
