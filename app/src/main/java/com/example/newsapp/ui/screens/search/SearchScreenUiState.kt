package com.example.newsapp.ui.screens.search

import com.example.newsapp.data.model.Doc

data class SearchScreenUiState(
    val articles: List<Doc> = emptyList()
)
