package com.example.newsapp.ui.screens.search

import com.example.newsapp.data.model.NewsArticle

data class SearchScreenUiState(
    var articles: List<NewsArticle> = emptyList()
)
