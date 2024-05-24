package com.example.newsapp.data.repositories.local

import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.entities.ArticleWithMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun getBookmarkArticles(): List<ArticleWithMedia>
    suspend fun getFavoriteArticles(): List<ArticleWithMedia>
    suspend fun addToBookmark(article: NewsArticle)
    suspend fun addToFavorites(article: NewsArticle)

    suspend fun insertRecentSearch(string: String)
    suspend fun getRecentSearch(): Flow<List<RecentSearchEntity>>
    suspend fun clearRecentSearch()
    suspend fun deleteSearchTerm(string: String)
}