package com.example.newsapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val TAG = "SEARCH SCREEN VIEWMODEL"
class SearchScreenViewModel(
    private val remoteRepository: RemoteRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<SearchScreenUiState> = MutableStateFlow(
        SearchScreenUiState()
    )
    private val _isSearch: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val uiState = _uiState.asStateFlow()
    val isSearch = _isSearch.asStateFlow()

    private var lastScrollIndex = 0
    private val _scrollUp = MutableStateFlow(false)
    val scrollUp: StateFlow<Boolean>
        get() = _scrollUp.asStateFlow()

    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }
    fun setIsSearch(){
        _isSearch.value = false
    }

//    fun searchArticle(query: String){
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "Calling the searchArticle function")
//            val results = remoteRepository.searchArticle(query)
//            Log.d(TAG, "Results: ${results.size}")
//            _uiState.value = SearchScreenUiState(articles = results)
//        }
//    }
    fun categoryHeadline(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Getting category headlines")
            val results = remoteRepository.getCategoryTopStories(category)
            Log.d(TAG, "Results: ${results.size}")
            _isSearch.value = true
            _uiState.value.articles = results
        }

    }
}