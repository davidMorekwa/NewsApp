package com.example.newsapp.data.repositories.local_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.repositories.local_data.entities.ArticleWithMedia
import com.example.newsapp.data.repositories.local_data.entities.NewsArticleEntity

@Dao
interface NewsDao {
    @Query("select * from tbl_news_articles where isBookmark = 1")
    suspend fun getBookmarkArticles() : List<ArticleWithMedia>
    @Query("select * from tbl_news_articles where isFavorite = 1")
    suspend fun getFavoriteArticles(): List<ArticleWithMedia>
    @Insert
    suspend fun addToFavorite(article: NewsArticleEntity)
    @Insert
    suspend fun addToBookmarks(article: NewsArticleEntity)

    @Query("DELETE FROM tbl_news_articles")
    suspend fun clearArticles()
}