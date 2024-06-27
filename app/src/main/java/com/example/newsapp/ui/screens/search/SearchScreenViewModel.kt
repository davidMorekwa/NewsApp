package com.example.newsapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.repositories.local_data.LocalRepository
import com.example.newsapp.data.repositories.local_data.entities.RecentSearchEntity
import com.example.newsapp.data.repositories.remote_data.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val TAG = "SEARCH SCREEN VIEWMODEL"
class SearchScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
): ViewModel() {
    private val _searchResultUiState: MutableStateFlow<SearchScreenUiState> = MutableStateFlow(
        SearchScreenUiState()
    )
    private val _searchString: MutableStateFlow<String> = MutableStateFlow("")
    private val _isSearch: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _recentSearch: MutableStateFlow<List<RecentSearchEntity>> = MutableStateFlow(emptyList())
    val searchResultUiState = _searchResultUiState.asStateFlow()
    val isSearch = _isSearch.asStateFlow()
    val recentSearch = _recentSearch.asStateFlow()
    val searchString = _searchString.asStateFlow()

    init {
        viewModelScope.launch {
            val recentSearch = localRepository.getRecentSearch()
            recentSearch.collect(){ value ->
                _recentSearch.value = value
            }
        }
    }

    private var lastScrollIndex = 0
    private val _scrollUp = MutableStateFlow(false)
    val scrollUp: StateFlow<Boolean>
        get() = _scrollUp.asStateFlow()
    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }
    fun setIsSearch(value: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _isSearch.value = value
        }
    }



    fun searchArticle(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Calling the searchArticle function")
            _searchString.value = query
            _searchResultUiState.value = SearchScreenUiState(articles = emptyList())
            val results = remoteRepository.searchArticle(query)
            Log.d(TAG, "Results: ${results.size}")
            setIsSearch(true)
            _searchResultUiState.value = SearchScreenUiState(articles = results.map { it -> it.toNewsArticle() })
            localRepository.insertRecentSearch(query)
        }
    }
    fun categoryHeadline(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            setIsSearch(false)
            _searchResultUiState.value = SearchScreenUiState(articles = emptyList())
            Log.d(TAG, "Getting ${category} headlines")
            _searchString.value = category
            val headlines_results = remoteRepository.getCategoryTopStories(category)
            val latest_news_results = remoteRepository.getCategoryLatestNews(category)
            val result = headlines_results+latest_news_results
            Log.d(TAG, "Results: ${result.size}")
            _searchResultUiState.value = SearchScreenUiState(articles = result.shuffled())

        }
    }
    fun deleteSearchTerm(string: String){
        viewModelScope.launch {
            localRepository.deleteSearchTerm(string)
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