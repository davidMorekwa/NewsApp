package com.example.newsapp.data.repositories.remote

import com.example.newsapp.data.model.Doc
import com.example.newsapp.data.model.NewsArticle

interface RemoteRepository {
    suspend fun getTopStories(): List<NewsArticle>
    suspend fun getCategoryTopStories(category: String): List<NewsArticle>
    suspend fun searchArticle(query: String): List<Doc>
}