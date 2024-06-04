package com.example.newsapp.data.model.response.topheadlines

import com.example.newsapp.data.model.NewsArticle

data class ApiResponse(
    var copyright: String,
    var num_results: Int,
    var results: List<NewsArticle>,
    var status: String
)