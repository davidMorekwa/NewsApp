package com.example.newsapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val TAG = "SEARCH SCREEN VIEWMODEL"
class SearchScreenViewModel(
    private val remoteRepository: RemoteRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<SearchScreenUiState> = MutableStateFlow(
        SearchScreenUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun searchArticle(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Calling the searchArticle function")
            val results = remoteRepository.searchArticle(query)
            Log.d(TAG, "Results: ${results.size}")
            _uiState.value = SearchScreenUiState(articles = results)
        }
    }
    fun categoryHeadline(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Getting category headlines")
            val results = remoteRepository.getCategoryTopStories(category)
            Log.d(TAG, "Results: ${results.size}")
        }

    }
}