package com.example.newsapp.ui.screens.home

import android.util.Log
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local.entities.ArticleAndMedia
import com.example.newsapp.data.repositories.local.entities.NewsArticleEntity
import com.example.newsapp.data.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val remoteRepository: RemoteRepository,
//    private val pager: Pager<Int, NewsArticleEntity>
): ViewModel() {
    private var uiState: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(emptyList())

    val state = uiState.asStateFlow()
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
            uiState.value = remoteRepository.getTopStories()
        }
    }

}