package com.example.newsapp.data.repositories.weather

import com.example.newsapp.data.apiservices.WeatherService
import com.example.newsapp.data.model.response.weather.WeatherResponse

class WeatherRepositoryImpl(
    private val weatherService: WeatherService
): WeatherRepository{
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse {
        return weatherService.getWeather(
            latitude = latitude,
            longitude = longitude,
        )
    }
}