package com.example.newsapp.data.repositories.remote

import com.example.newsapp.data.model.response.search.Doc
import com.example.newsapp.data.model.NewsArticle

interface RemoteRepository {
    //Top Headlines
    suspend fun getTopStories(): List<NewsArticle>
    suspend fun getCategoryTopStories(category: String): List<NewsArticle>
    // Search article
    suspend fun searchArticle(query: String): List<Doc>
    // Latest News articles
    suspend fun getLatestNews(): List<NewsArticle>
}