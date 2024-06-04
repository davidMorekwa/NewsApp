package com.example.newsapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.LocalRepository
import com.example.newsapp.data.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
//    private val pager: Pager<Int, NewsArticleEntity>
): ViewModel() {
    private var _topHeadlineUiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    private var _latestNewsUiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    private val isRefreshing =  MutableStateFlow(false)
    val topHeadlinesState = _topHeadlineUiState.asStateFlow()
    val latestNewsState = _latestNewsUiState.asStateFlow()
    val isRefereshing = isRefreshing.asStateFlow()
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

}