package com.example.newsapp.data.repositories.remote.services

import com.example.newsapp.data.model.response.category.CategoryResponse
import com.example.newsapp.data.model.response.latestnews.LatestNewsResponse
import com.example.newsapp.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestNewsService {
    @GET("all.json")
    suspend fun getLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("arts.json")
    suspend fun getArtsLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("automobiles.json")
    suspend fun getAutomobilesLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("books.json")
    suspend fun getBooksLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("sunday review.json")
    suspend fun getSundayReviewLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("business.json")
    suspend fun getBusinessLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("fashion.json")
    suspend fun getFashionLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("food.json")
    suspend fun getFoodLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("health.json")
    suspend fun getHealthLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("home & garden.json")
    suspend fun getHomeAndGardenLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("times insider.json")
    suspend fun getTimesInsiderLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("magazine.json")
    suspend fun getMagazineLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("movies.json")
    suspend fun getMoviesLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("opinion.json")
    suspend fun getOpinionLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("real estate.json")
    suspend fun getRealEstateLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("science.json")
    suspend fun getScienceLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("sports.json")
    suspend fun getSportsLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("technology.json")
    suspend fun getTechnologyLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("travel.json")
    suspend fun getTravelLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("u.s..json")
    suspend fun getUSLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>
    @GET("world.json")
    suspend fun getWorldLatestNews(
        @Query("api-key") key: String = Constants.key
    ): Response<LatestNewsResponse>

    @GET("https://api.nytimes.com/svc/news/v3/content/section-list.json")
    suspend fun getCategoryList(
        @Query("api-key") key: String = Constants.key
    ): Response<CategoryResponse>
}