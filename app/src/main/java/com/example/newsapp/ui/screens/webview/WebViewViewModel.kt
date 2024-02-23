package com.example.newsapp.ui.screens.webview

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WebViewViewModel: ViewModel() {
    val TAG = "WEBVIEW VIEWMODEL"
    private var _uiState: MutableStateFlow<WebViewUiState> = MutableStateFlow(WebViewUiState())
    var uistate = _uiState.asStateFlow()

    init {
        Log.d(TAG, "url: ${_uiState.value.url}")
    }

    fun readArticle(article: NewsArticle) {
        _uiState.value = WebViewUiState(url = article.htmlurl)
    }
}
