package com.example.newsapp.data.repositories.weather

import com.example.newsapp.data.model.response.weather.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ):WeatherResponse
}

