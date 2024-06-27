package com.example.newsapp.data.di

import android.content.Context
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.apiservices.LatestNewsService
import com.example.newsapp.data.apiservices.TopHeadlineNewsService
import com.example.newsapp.data.apiservices.WeatherService
import com.example.newsapp.data.repositories.auth.AuthRepository
import com.example.newsapp.data.repositories.auth.AuthRepositoryImpl
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.repositories.local_data.LocalRepositoryImpl
import com.example.newsapp.data.repositories.local_data.NewsDatabase
import com.example.newsapp.data.repositories.remote_data.RemoteRepository
import com.example.newsapp.data.repositories.remote_data.RemoteRepositoryImpl
import com.example.newsapp.data.repositories.weather.WeatherRepository
import com.example.newsapp.data.repositories.weather.WeatherRepositoryImpl
import com.example.newsapp.data.utils.Constants
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val topHeadlineNewsService: TopHeadlineNewsService
    val latestNewsService: LatestNewsService
    val weatherService: WeatherService
    val remoteRepository: RemoteRepository
    val weatherRepository: WeatherRepository
    val localRepository: LocalRepository
    val authRepository: AuthRepository
    val generativeModel: GenerativeModel
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
    private val weather_retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override val topHeadlineNewsService: TopHeadlineNewsService by lazy {
        top_headlines_retrofit.create(TopHeadlineNewsService::class.java)
    }
    override val latestNewsService: LatestNewsService by lazy {
        latest_news_retrofit.create(LatestNewsService::class.java)
    }
    override val weatherService: WeatherService by lazy {
        weather_retrofit.create(WeatherService::class.java)
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
    override val weatherRepository: WeatherRepository by lazy{
        WeatherRepositoryImpl(weatherService = weatherService)
    }
    override val generativeModel: GenerativeModel by lazy {
        GenerativeModel(
            "gemini-1.5-flash",
            // Retrieve API key as an environmental variable defined in a Build Configuration
            // see https://github.com/google/secrets-gradle-plugin for further instructions
            BuildConfig.geminiApiKey,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 64
                topP = 0.95f
                maxOutputTokens = 8192
                responseMimeType = "text/plain"
            },
            // safetySettings = Adjust safety settings
            // See https://ai.google.dev/gemini-api/docs/safety-settings
        )
    }
}