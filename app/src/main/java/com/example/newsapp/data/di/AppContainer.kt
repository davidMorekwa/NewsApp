package com.example.newsapp.data.di

import android.content.Context
import com.example.newsapp.data.repositories.auth.AuthRepository
import com.example.newsapp.data.repositories.auth.AuthRepositoryImpl
import com.example.newsapp.data.repositories.local.LocalRepository
import com.example.newsapp.data.repositories.local.LocalRepositoryImpl
import com.example.newsapp.data.repositories.local.NewsDatabase
import com.example.newsapp.data.repositories.remote.services.TopHeadlineNewsService
import com.example.newsapp.data.repositories.remote.RemoteRepository
import com.example.newsapp.data.repositories.remote.RemoteRepositoryImpl
import com.example.newsapp.data.repositories.remote.services.LatestNewsService
import com.example.newsapp.data.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val topHeadlineNewsService: TopHeadlineNewsService
    val latestNewsService: LatestNewsService
    val remoteRepository: RemoteRepository
    val localRepository: LocalRepository
    val authRepository: AuthRepository
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

    // Retrofit Builders

    private val top_headlines_retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.TOP_HEADLINES_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val latest_news_retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.LATEST_NEWS_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override val topHeadlineNewsService: TopHeadlineNewsService by lazy {
        top_headlines_retrofit.create(TopHeadlineNewsService::class.java)
    }
    override val latestNewsService: LatestNewsService by lazy {
        latest_news_retrofit.create(LatestNewsService::class.java)
    }
    override val remoteRepository: RemoteRepository by lazy {
        RemoteRepositoryImpl(
            topHeadlineNewsService = topHeadlineNewsService,
            latestNewsService = latestNewsService,
            newsDatabase = database
        )
    }
    override val localRepository: LocalRepository by lazy{
        LocalRepositoryImpl(
            newsDatabase = database
        )
    }
    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(firebaseAuth = firebaseAuth)
    }
//    @OptIn(ExperimentalPagingApi::class)
//    override val pager: Pager<Int, NewsArticleEntity>  =
//        Pager(
//            config = PagingConfig(pageSize = 10),
//            pagingSourceFactory = {localRepository.getNewsArticles()}
//        )
}