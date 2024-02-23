package com.example.newsapp.data.repositories.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.newsapp.data.utils.Constants

data class ArticleAndMedia(
    @Embedded val article: NewsArticleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val media: List<MultimediaEntity>
)
