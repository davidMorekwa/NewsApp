package com.example.newsapp.data.repositories.local

import androidx.paging.PagingSource
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getNewsArticles(): List<ArticleAndMedia>
    suspend fun insertArticles(articles: List<NewsArticleEntity>)
    suspend fun clearArticles()

    suspend fun insertRecentSearch(string: String)
    suspend fun getRecentSearch(): Flow<List<RecentSearchEntity>>
    suspend fun clearRecentSearch()
    suspend fun deleteSearchTerm(string: String)
}