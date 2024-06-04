package com.example.newsapp.data.repositories.remote.services

import com.example.newsapp.data.model.response.latestnews.LatestNewsResponse
import com.example.newsapp.data.model.response.topheadlines.ApiResponse
import com.example.newsapp.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestNewsService {
    @GET("all.json")
    suspend fun getLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
}