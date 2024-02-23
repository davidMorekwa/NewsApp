package com.example.newsapp.data.repositories.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM tbl_news_articles")
    fun getNewsArticles(): List<ArticleAndMedia>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(article: NewsArticleEntity)

    @Query("DELETE FROM tbl_news_articles")
    suspend fun clearArticles()
}