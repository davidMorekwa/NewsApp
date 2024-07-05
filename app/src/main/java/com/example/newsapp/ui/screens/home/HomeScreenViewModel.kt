package com.example.newsapp.ui.screens.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.response.weather.WeatherResponse
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.repositories.local_data.entities.NewsCategoryEntity
import com.example.newsapp.data.repositories.remote_data.RemoteRepository
import com.example.newsapp.data.repositories.weather.WeatherRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val generativeModel: GenerativeModel,
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val application: NewsApplication
): ViewModel() {
    private var _topHeadlineUiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    private var _latestNewsUiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    private var _weatherUiState: MutableStateFlow<WeatherUiState> = MutableStateFlow(WeatherUiState.Loading)
    private val _isRefreshing =  MutableStateFlow(false)
    private var _chatHistory : MutableStateFlow<List<Content>> = MutableStateFlow(emptyList())
    private var _chatHistoryState :MutableStateFlow<GeminiUiState> = MutableStateFlow(GeminiUiState.Loading)
    private var _categoryIndex = MutableStateFlow(0)
    private var categoryNewsMap:MutableMap<String, List<NewsArticle>> = LinkedHashMap()
    private var _articleCount = MutableStateFlow(0)
    private var _loadedCategories:MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
//    private var _selectedCategoryNewsListState: MutableStateFlow<ArrayMap<String, List<NewsArticle>>> = MutableStateFlow(
//        ArrayMap()
//    )
    private var _selectedCategoryNewsListState:MutableStateFlow<CategoryNewsUiState> = MutableStateFlow( CategoryNewsUiState.Loading)
    private var _selectedCategories = MutableStateFlow<List<NewsCategoryEntity>>(emptyList())

    val topHeadlinesState = _topHeadlineUiState.asStateFlow()
    val latestNewsState = _latestNewsUiState.asStateFlow()
    val isRefereshing = _isRefreshing.asStateFlow()
    val chatHistoryState = _chatHistoryState.asStateFlow()
    val weatherState = _weatherUiState.asStateFlow()
    val selectedCategories = _selectedCategories.asStateFlow()
    val selectedCategoryNewsListState = _selectedCategoryNewsListState.asStateFlow()
    val categoryIndex = _categoryIndex.asStateFlow()
    val articleCount = _articleCount.asStateFlow()

    val TAG = "HOMESCREEN VIEW MODEL"

    private var lastScrollIndex = 0

    private val _scrollUp = MutableStateFlow(false)
    val scrollUp: StateFlow<Boolean>
        get() = _scrollUp.asStateFlow()

    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }
//    fun updateCategoryIndex(index: Int){
//        if(index <= _selectedCategories.value.size-1) {
//            _categoryIndex.value = index
//            Log.d(TAG, "Category Index: ${_categoryIndex.value}")
//            fetchOtherArticles(_categoryIndex.value)
//        }
//    }

    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            Log.d(TAG, "Broadcast receiver called. Latitude: $latitude, Longitude: $longitude")
            if(latitude != 0.0 && longitude != 0.0) {
                getWeather(longitude, latitude)
            }
        }
    }

    init{
        viewModelScope.launch(Dispatchers.IO) {
            fetchInitialArticles()
            LocalBroadcastManager.getInstance(application)
                .registerReceiver(locationReceiver, IntentFilter("LocationUpdate"))
        }
    }
    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(application).unregisterReceiver(locationReceiver)
    }

    fun fetchInitialArticles(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Getting articles")

            val topStoriesDeferred = async { remoteRepository.getTopStories() }
            val latestNewsDeferred = async { remoteRepository.getLatestNews() }
            _selectedCategories.value = localRepository.getCategories()
            val topStories = topStoriesDeferred.await()
            val latestNews = latestNewsDeferred.await()
//
            _topHeadlineUiState.value = topStories
            _latestNewsUiState.value = latestNews
            _articleCount.value = latestNews.size
            _selectedCategories.value.forEach { it ->
                categoryNewsMap[it.name] = emptyList()
            }
            Log.d(TAG, "Latest Articles: ${_latestNewsUiState.value.size}")
            Log.d(TAG, "CategoryNewsMap: ${categoryNewsMap.keys.toList()}")
        }
    }
    fun fetchOtherArticles(index: Int) {
        if (index <= _selectedCategories.value.size - 1) {
            val category = _selectedCategories.value[index].name
            Log.d(TAG, "Lazy load category ${category}")
            fetchCategoryNews(category)
            _loadedCategories.value = _loadedCategories.value+category
            _categoryIndex.value = index + 1
        }
    }
    fun fetchCategoryNews(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!_loadedCategories.value.contains(category)) {
                    val articles = remoteRepository.getCategoryLatestNews(category)
                    if (articles.isNotEmpty()) {
                        categoryNewsMap[category] = articles
                        _articleCount.value += articles.size
                        Log.d(TAG, "New Article Count ${_articleCount.value}")
                        _selectedCategoryNewsListState.value = CategoryNewsUiState.Success(LinkedHashMap(categoryNewsMap))
                        (_selectedCategoryNewsListState.value as CategoryNewsUiState.Success)
                            .articles.forEach { (key, value) -> Log.d(TAG, "Updated Category News ${key} : ${value.size}") }
                    } else {
                        if (_categoryIndex.value.plus(1) >= _selectedCategories.value.size -1) {
                            _categoryIndex.value += 1
                            var newCategoryToFetch = _selectedCategories.value[_categoryIndex.value].name
                            Log.d(TAG, "Fetching from another Category: ${newCategoryToFetch}")
                            val newArticlesFetched = remoteRepository.getCategoryLatestNews(newCategoryToFetch)
                            _articleCount.value += newArticlesFetched.size
                            categoryNewsMap[newCategoryToFetch] = newArticlesFetched
                            Log.d(TAG, "CategoryNewsMap Keys ${categoryNewsMap.keys}")
                            _selectedCategoryNewsListState.value = CategoryNewsUiState.Success(LinkedHashMap(categoryNewsMap))
                            _loadedCategories.value = _loadedCategories.value + newCategoryToFetch
                        }
                    }
                    _loadedCategories.value = _loadedCategories.value + category

                }

            } catch (e: HttpException){
                _selectedCategoryNewsListState.value = CategoryNewsUiState.Error(e.message() ?: "Unknown error")
            }
        }
    }

    fun getWeather(longitude: Double, latitude: Double){
        viewModelScope.launch(Dispatchers.Unconfined) {
            try {
                val weather = async{weatherRepository.getWeather(latitude, longitude)}
                _weatherUiState.value = WeatherUiState.Success(weather.await())
            } catch (e: HttpException) {
                _weatherUiState.update { WeatherUiState.Error(e) }
            }
        }
    }


    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Refresh For new Articles")
            _isRefreshing.value = true
            val articles = remoteRepository.getLatestNews()
            val currentArticles = _latestNewsUiState.value
            val newArticles = articles.filter { newArticle ->
                newArticle !in currentArticles
            }
            Log.d(TAG, "FRESH ARTICLES: ${newArticles.size}")
            _latestNewsUiState.value = newArticles + currentArticles
            _isRefreshing.value = false
        }
    }

    fun getCategoryTopStories(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            _topHeadlineUiState.value = emptyList()
            val results = remoteRepository.getCategoryTopStories(category)
            _topHeadlineUiState.value = results
        }
    }

    fun addToFavorites(article: NewsArticle){
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.addToFavorites(article)
        }
    }

    fun addToBookmarks(article: NewsArticle){
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.addToBookmark(article)
        }
    }

    fun getArticleSummary(articleURL: String){
        viewModelScope.launch(Dispatchers.IO) {
            _chatHistory.value += content("user") {
                text("Summarize this article: $articleURL")
            }
            _chatHistoryState.value = GeminiUiState.Success(_chatHistory.value, isResponseLoading = true)
            try {
                val chat = generativeModel.startChat(_chatHistory.value)
                val response = chat.sendMessage("summarize this article: $articleURL")
                _chatHistory.value += response.candidates.first().content
                _chatHistoryState.value = GeminiUiState.Success(_chatHistory.value, isResponseLoading = false)
            } catch (e: Exception) {
                _chatHistoryState.value = GeminiUiState.Error(e.message ?: "Unknown error")
            }
            for(content in _chatHistory.value){
                Log.d(TAG, "AI MODEL RESPONSE: ${content.parts.first().asTextOrNull()}")
            }
        }
    }

    fun sendMessage(message: String){
        viewModelScope.launch(Dispatchers.IO) {
            _chatHistory.value += content("user") {
                text(message)
            }
            _chatHistoryState.value = GeminiUiState.Success(_chatHistory.value, isResponseLoading = true)
            try {
                val chat = generativeModel.startChat(_chatHistory.value)
                val response = chat.sendMessage(message)
                _chatHistory.value += response.candidates.first().content
                _chatHistoryState.value = GeminiUiState.Success(_chatHistory.value, isResponseLoading = false)
            } catch (e: Exception) {
                _chatHistoryState.value = GeminiUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

}

sealed class GeminiUiState {
    object Loading : GeminiUiState()
    data class Success(
        val chatHistory: List<Content>,
        val isResponseLoading: Boolean = true
    ) : GeminiUiState()
    data class Error(val message: String) : GeminiUiState()
}
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: WeatherResponse) : WeatherUiState()
    data class Error(val message: HttpException) : WeatherUiState()
}
sealed class CategoryNewsUiState{
    object Loading : CategoryNewsUiState()
    data class Success(var articles: MutableMap<String, List<NewsArticle>>) : CategoryNewsUiState()
    data class Error(val message: String) : CategoryNewsUiState()
}