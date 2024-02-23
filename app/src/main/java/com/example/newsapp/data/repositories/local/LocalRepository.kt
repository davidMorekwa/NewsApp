package com.example.newsapp.data.repositories.local

import androidx.paging.PagingSource
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity

interface LocalRepository {
    fun getNewsArticles(): List<ArticleAndMedia>
    suspend fun insertArticles(articles: List<NewsArticleEntity>)
    suspend fun clearArticles()
}