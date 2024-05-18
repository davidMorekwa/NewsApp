package com.example.newsapp.data.repositories.local

import androidx.paging.PagingSource
import com.example.newsapp.data.repositories.local.dao.NewsDao
import com.example.newsapp.data.repositories.local.dao.RecentSearchDao
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val newsDao: NewsDao,
    private val recentSearchDao: RecentSearchDao
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

    override suspend fun insertRecentSearch(string: String) {
        return recentSearchDao.insert(RecentSearchEntity(string))
    }

    override suspend fun getRecentSearch(): Flow<List<RecentSearchEntity>> {
        return recentSearchDao.getRecentSearch()
    }

    override suspend fun clearRecentSearch() {
        return recentSearchDao.clearRecentSearch()
    }

    override suspend fun deleteSearchTerm(string: String) {
        return recentSearchDao.deleteSearchTerm(string)
    }
}