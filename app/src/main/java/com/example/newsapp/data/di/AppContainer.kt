package com.example.newsapp.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import com.example.newsapp.data.repositories.local.LocalRepository
import com.example.newsapp.data.repositories.local.LocalRepositoryImpl
import com.example.newsapp.data.repositories.local.NewsDatabase
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.remote.NewsApiService
import com.example.newsapp.data.repositories.remote.NewsArticlesRemoteMediator
import com.example.newsapp.data.repositories.remote.RemoteRepository
import com.example.newsapp.data.repositories.remote.RemoteRepositoryImpl
import com.example.newsapp.data.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val newsApiService: NewsApiService
    val remoteRepository: RemoteRepository
    val localRepository: LocalRepository
//    val pager: Pager<Int, NewsArticleEntity>
}

class AppContainerImpl(context: Context): AppContainer{
    val database: NewsDatabase = NewsDatabase.getDatabaseInstance(context)
    val newsDao = database.newsDao()
    val recentSearchDao = database.recentSearchDao()
    val multimediaDao = database.multimediaDao()
    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    override val newsApiService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
    override val remoteRepository: RemoteRepository by lazy {
        RemoteRepositoryImpl(newsApiService = newsApiService, newsDatabase = database)
    }
    override val localRepository: LocalRepository by lazy{
        LocalRepositoryImpl(
            newsDao = newsDao,
            recentSearchDao = recentSearchDao
        )
    }
//    @OptIn(ExperimentalPagingApi::class)
//    override val pager: Pager<Int, NewsArticleEntity>  =
//        Pager(
//            config = PagingConfig(pageSize = 10),
//            pagingSourceFactory = {localRepository.getNewsArticles()}
//        )
}