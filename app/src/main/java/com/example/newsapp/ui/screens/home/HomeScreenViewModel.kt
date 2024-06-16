package com.example.newsapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.LocalRepository
import com.example.newsapp.data.repositories.remote.RemoteRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val generativeModel: GenerativeModel
//    private val pager: Pager<Int, NewsArticleEntity>
): ViewModel() {
    private var _topHeadlineUiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    private var _latestNewsUiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    private val isRefreshing =  MutableStateFlow(false)
    private var _chatHistory : MutableStateFlow<List<Content>> = MutableStateFlow(emptyList())
    private var _chatHistoryState :MutableStateFlow<GeminiUiState> = MutableStateFlow(GeminiUiState.Loading)
    val topHeadlinesState = _topHeadlineUiState.asStateFlow()
    val latestNewsState = _latestNewsUiState.asStateFlow()
    val isRefereshing = isRefreshing.asStateFlow()
    val chatHistoryState = _chatHistoryState.asStateFlow()
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

    init{
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Getting articles")
            _topHeadlineUiState.value = remoteRepository.getTopStories()
            _latestNewsUiState.value = remoteRepository.getLatestNews()
            Log.d(TAG, "Latest Articles: ${_latestNewsUiState.value.size}")
        }
    }

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Refresh For new Articles")
            isRefreshing.value = true
            val articles = remoteRepository.getLatestNews()
            val currentArticles = _latestNewsUiState.value
            val newArticles = articles.filter { newArticle ->
                newArticle !in currentArticles
            }
            Log.d(TAG, "FRESH ARTICLES: ${newArticles.size}")
            _latestNewsUiState.value = newArticles + currentArticles
            isRefreshing.value = false
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