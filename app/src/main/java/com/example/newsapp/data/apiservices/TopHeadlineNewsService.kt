package com.example.newsapp.data.apiservices

import com.example.newsapp.data.model.response.search.SearchResponse
import com.example.newsapp.data.model.response.topheadlines.ApiResponse
import com.example.newsapp.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopHeadlineNewsService {
    @GET("home.json")
    suspend fun getTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("arts.json")
    suspend fun getArtsTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("automobiles.json")
    suspend fun getAutomobilesTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("business.json")
    suspend fun getBusinessTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("fashion.json")
    suspend fun getFashionTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("food.json")
    suspend fun getFoodTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("health.json")
    suspend fun getHealthTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("home.json")
    suspend fun getHomeTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("insider.json")
    suspend fun getInsiderTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("magazine.json")
    suspend fun getMagazineTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("movies.json")
    suspend fun getMoviesTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("opinion.json")
    suspend fun getOpinionTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("politics.json")
    suspend fun getPoliticsTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("realestate.json")
    suspend fun getRealEstateTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("science.json")
    suspend fun getScienceTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("sports.json")
    suspend fun getSportsTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("technology.json")
    suspend fun getTechnologyTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("travel.json")
    suspend fun getTravelTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("us.json")
    suspend fun getUSTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>
    @GET("world.json")
    suspend fun getWorldTopStories(
        @Query("api-key") key: String = Constants.nyt_key
    ): Response<ApiResponse>

    @GET("https://api.nytimes.com/svc/search/v2/articlesearch.json")
    suspend fun searchArticle(
        @Query("api-key") key: String = Constants.nyt_key,
        @Query("q") query: String,
    ): Response<SearchResponse>
}