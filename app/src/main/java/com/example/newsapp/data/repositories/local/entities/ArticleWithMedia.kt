package com.example.newsapp.data.repositories.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleWithMedia(
    @Embedded val article: NewsArticleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    val media: List<MultimediaEntity>
)
