package com.example.newsapp.data.repository.weather

import com.example.newsapp.data.fakeDataSource.RemoteDataSource
import com.example.newsapp.data.repositories.weather.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {
    lateinit var weatherRepository: WeatherRepository

    @Before
    fun before_test(){
        weatherRepository = FakeWatherRepository()
    }
    @Test
    fun weatherRepository_getWeather_assertWeather(){
        runBlocking {
            val actual = weatherRepository.getWeather(1.0, 1.0)
            val expected = RemoteDataSource.weather
            Assert.assertEquals(expected, actual)
        }
    }

}