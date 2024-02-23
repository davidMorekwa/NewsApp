package com.example.newsapp.data.repositories.local

import androidx.paging.PagingSource
import com.example.newsapp.data.repositories.local.dao.NewsDao
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity

class LocalRepositoryImpl(
    private val newsDao: NewsDao
): LocalRepository {
    override suspend fun insertArticles(articles: List<NewsArticleEntity>) {
        articles.forEach { it->
            newsDao.insertArticles(it)
        }
    }

    override suspend fun clearArticles() {
        newsDao.clearArticles()
    }

    override fun getNewsArticles(): List<ArticleAndMedia> {
        return newsDao.getNewsArticles()
    }
}