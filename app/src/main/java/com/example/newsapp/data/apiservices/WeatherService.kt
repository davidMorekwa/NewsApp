package com.example.newsapp.data.apiservices

import com.example.newsapp.data.model.response.weather.WeatherResponse
import com.example.newsapp.data.utils.Constants.weather_key
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = weather_key,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}