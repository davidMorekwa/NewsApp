package com.example.newsapp.data.repositories.weather

import android.util.Log
import com.example.newsapp.data.apiservices.WeatherService
import com.example.newsapp.data.model.response.weather.WeatherResponse

private const val TAG = "WEATHER REPOSITORY"
class WeatherRepositoryImpl(
    private val weatherService: WeatherService
): WeatherRepository{
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse {
        Log.d(TAG, "Getting Weather")
        return weatherService.getWeather(
            latitude = latitude,
            longitude = longitude,
        )
    }
}