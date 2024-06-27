package com.example.newsapp.data.repositories.local_data

import androidx.room.withTransaction
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local_data.entities.ArticleWithMedia
import com.example.newsapp.data.repositories.local_data.entities.NewsCategoryEntity
import com.example.newsapp.data.repositories.local_data.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val newsDatabase: NewsDatabase
): LocalRepository {
    override suspend fun getBookmarkArticles(): List<ArticleWithMedia> {
        return newsDatabase.newsDao().getBookmarkArticles()
    }

    override suspend fun getFavoriteArticles(): List<ArticleWithMedia> {
        return newsDatabase.newsDao().getFavoriteArticles()
    }

    override suspend fun addToBookmark(article: NewsArticle) {
        val multimedia = article.multimedia
        newsDatabase.withTransaction {
            val articleEntity = article.toNewsArticleEntity()
            articleEntity.isBookmark = true
            val multimediaEntityList = multimedia?.map { it ->
                it.toMultimediaEntity(articleEntity.id)
            }
            if (multimediaEntityList != null) {
                newsDatabase.multimediaDao().insertMultimedia(multimediaEntityList)
            }
            newsDatabase.newsDao().addToBookmarks(article = articleEntity)
        }
    }

    override suspend fun addToFavorites(article: NewsArticle) {
        val multimedia = article.multimedia
        newsDatabase.withTransaction {
            val articleEntity = article.toNewsArticleEntity()
            articleEntity.isFavorite = true
            val multimediaEntityList = multimedia?.map { it ->
                it.toMultimediaEntity(articleEntity.id)
            }
            if (multimediaEntityList != null) {
                newsDatabase.multimediaDao().insertMultimedia(multimediaEntityList)
            }
            newsDatabase.newsDao().addToFavorite(article = articleEntity)
        }
    }

    override suspend fun insertRecentSearch(string: String) {
        return newsDatabase.recentSearchDao().insert(RecentSearchEntity(string))
    }

    override suspend fun getRecentSearch(): Flow<List<RecentSearchEntity>> {
        return newsDatabase.recentSearchDao().getRecentSearch()
    }

    override suspend fun clearRecentSearch() {
        return newsDatabase.recentSearchDao().clearRecentSearch()
    }

    override suspend fun deleteSearchTerm(string: String) {
        return newsDatabase.recentSearchDao().deleteSearchTerm(string)
    }

    override suspend fun addCategory(categoryEntities: List<NewsCategoryEntity>) {
        categoryEntities.forEach { categoryEntity ->
            newsDatabase.newsCategoryDao().addCategory(categoryEntity)
        }
    }

    override suspend fun getCategories(): List<NewsCategoryEntity> {
        return newsDatabase.newsCategoryDao().getCategories()
    }

    override suspend fun deleteCategory(categoryEntity: NewsCategoryEntity) {
        return newsDatabase.newsCategoryDao().deleteCategory(categoryEntity)
    }

    override suspend fun clearCategories() {
        return newsDatabase.newsCategoryDao().clearCategories()
    }
}