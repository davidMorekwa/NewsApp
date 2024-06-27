package com.example.newsapp.data.repositories.remote_data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.repositories.local_data.NewsDatabase
import com.example.newsapp.data.repositories.local_data.entities.NewsArticleEntity
import retrofit2.HttpException
import java.io.IOException



@OptIn(ExperimentalPagingApi::class)
class NewsArticlesRemoteMediator (
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val newsDatabase: NewsDatabase
): RemoteMediator<Int, NewsArticleEntity>() {
    val TAG = "NEWS ARTICLE REMOTE MEDIATOR"
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsArticleEntity>
    ): MediatorResult {
        return try {
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) {
//                        1
//                    } else {
//                        (lastItem.id?.div(state.config.pageSize))?.plus(1)
//                    }
//                }
//
//                LoadType.APPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//            }
            Log.d(TAG, "LoadType: ${loadType}")
            val articles = remoteRepository.getTopStories()
            Log.d(TAG, "Articles retrieved: ${articles?.size}")
//            newsDatabase.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    Log.d(TAG, "Clearing database")
//                    localRepository.clearArticles()
//                }
//                Log.d(TAG, "Inserting articles into local database")
//                val newsArticleEntityList = articles.map { article: NewsArticle ->
//                    article.toNewsArticleEntity()
//                }
//                localRepository.insertArticles(newsArticleEntityList)
//            }
            MediatorResult.Success(endOfPaginationReached = articles.isEmpty() ?: true)
        } catch (e: IOException){
            MediatorResult.Error(e)
        } catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}