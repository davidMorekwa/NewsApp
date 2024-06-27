package com.example.newsapp.ui

import android.util.Log
import com.example.newsapp.data.fakeDataSource.RemoteDataSource
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.repositories.remote_data.RemoteRepository
import com.example.newsapp.data.repositories.weather.WeatherRepository
import com.example.newsapp.ui.screens.home.CategoryNewsUiState
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.home.WeatherUiState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.clearAllCaches
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Field
import java.lang.reflect.Modifier


fun createHttpException(): HttpException {
    val responseBody = "{\"message\":\"An error Occurred\"}"
        .toResponseBody("application/json".toMediaTypeOrNull())
    val response = Response.error<Any>(500, responseBody)
    return HttpException(response)
}
@Throws(Exception::class)
private fun setFinalStatic(field: Field, newValue: Any) {
    field.setAccessible(true)

    val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
    modifiersField.setAccessible(true)
    modifiersField.setInt(field, field.getModifiers() and Modifier.FINAL.inv())

    field.set(null, newValue)
}

const val TAG = "HOMESCREEN VIEWMODEL TEST"

@RunWith(MockitoJUnitRunner::class)
class HomeScreenViewmodelTest {
    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var generativeModel: GenerativeModel

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var httpException: HttpException

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        viewModel = HomeScreenViewModel(
            remoteRepository,
            localRepository,
            generativeModel,
            weatherRepository,
            testDispatcher
        )
        httpException = createHttpException()
        mockStatic(Log::class.java)
        `when`(Log.d(anyString(), anyString())).thenReturn(0)
        `when`(Log.e(anyString(), anyString())).thenReturn(0)
        `when`(Log.i(anyString(), anyString())).thenReturn(0)
        `when`(Log.v(anyString(), anyString())).thenReturn(0)
        `when`(Log.w(anyString(), anyString())).thenReturn(0)
    }
    @After
    fun tearDown() {
        clearAllCaches()
    }

    @Test
    fun viewmodel_getWeather_assertWeather() {
        runTest(testDispatcher) {
            val expected = RemoteDataSource.weather
            whenever(weatherRepository.getWeather(1.0, 1.0)).thenReturn(expected)

            viewModel.getWeather()

            val actualUIState = viewModel.weatherState.value
            val expectedUIState = WeatherUiState.Success(expected)
            Assert.assertEquals(expectedUIState, actualUIState)
        }
    }
    @Test
    fun viewmodel_fetchCategoryNews_assertCategoryNewsListUiState(){
        runTest(testDispatcher) {
//            val categories = whenever(localRepository.getCategories()).thenReturn(RemoteDataSource.categories)
            val categories = RemoteDataSource.categories
            for(category in categories){
                whenever(remoteRepository.getCategoryLatestNews(category.name)).thenReturn(RemoteDataSource.newsArticles.filter { it -> it.section == category.name })
                viewModel.fetchCategoryNews(category.name)
            }
            val uiStateMap = androidx.collection.ArrayMap<String, List<NewsArticle>>()
            uiStateMap["Section 1"] = listOf(RemoteDataSource.newsArticles[0])
            uiStateMap["Section 2"] = listOf(RemoteDataSource.newsArticles[1])
            uiStateMap["Section 3"] = listOf(RemoteDataSource.newsArticles[2])
            val expectedUIState = CategoryNewsUiState.Success(articles = uiStateMap)
            val actualUIState = viewModel.selectedCategoryNewsListState.value
            Assert.assertEquals(expectedUIState, actualUIState)
        }
    }
    @Test
    fun viewmodel_fetchArticles_assertCategoryArticles(){
        runTest(testDispatcher) {
            whenever(localRepository.getCategories()).thenReturn(RemoteDataSource.categories)
            whenever(remoteRepository.getLatestNews()).thenReturn(RemoteDataSource.latestNewsArticles)
            whenever(remoteRepository.getTopStories()).thenReturn(RemoteDataSource.newsArticles)
            val selectedCategories = RemoteDataSource.categories
            println("Selected Categories: ${selectedCategories.size}")
            for (category in selectedCategories){
                whenever(remoteRepository.getCategoryLatestNews(category.name)).thenReturn(RemoteDataSource.newsArticles.filter { it -> it.section == category.name })
            }

            viewModel.fetchInitialArticles()

            val expectedTopHeadlinesState = RemoteDataSource.newsArticles
            val expectedLatestNewsState = RemoteDataSource.latestNewsArticles
            val uiStateMap = androidx.collection.ArrayMap<String, List<NewsArticle>>()
            uiStateMap["Section 1"] = listOf(RemoteDataSource.newsArticles[0])
            uiStateMap["Section 2"] = listOf(RemoteDataSource.newsArticles[1])
            uiStateMap["Section 3"] = listOf(RemoteDataSource.newsArticles[2])
            val expectedCategoryNewsUiState = CategoryNewsUiState.Success(articles = uiStateMap)

            val actualCategoryNewsUiState = viewModel.selectedCategoryNewsListState.value
            val actualTopHeadlinesState = viewModel.topHeadlinesState.value
            val actualLatestNewsState = viewModel.latestNewsState.value

            Assert.assertEquals(expectedCategoryNewsUiState, actualCategoryNewsUiState)
            Assert.assertEquals(expectedLatestNewsState, actualLatestNewsState)
            Assert.assertEquals(actualTopHeadlinesState, expectedTopHeadlinesState)

        }
    }
}