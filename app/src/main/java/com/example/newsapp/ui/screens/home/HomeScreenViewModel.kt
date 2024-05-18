package com.example.newsapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val remoteRepository: RemoteRepository,
//    private val pager: Pager<Int, NewsArticleEntity>
): ViewModel() {
    private var _uiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())
    val state = _uiState.asStateFlow()
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
            _uiState.value = remoteRepository.getTopStories()
        }
    }

    fun getCategoryTopStories(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            val results = remoteRepository.getCategoryTopStories(category)
            _uiState.value = results
        }
    }

}