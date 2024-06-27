package com.example.newsapp.data.repository.weather

import com.example.newsapp.data.fakeDataSource.RemoteDataSource
import com.example.newsapp.data.model.response.weather.WeatherResponse
import com.example.newsapp.data.repositories.weather.WeatherRepository

class FakeWatherRepository: WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse {
        return RemoteDataSource.weather
    }
}