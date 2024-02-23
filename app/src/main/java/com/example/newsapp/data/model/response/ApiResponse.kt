package com.example.newsapp.data.model.response

import com.example.newsapp.data.model.NewsArticle

data class ApiResponse(
    var copyright: String,
    var last_updated: String,
    var num_results: Int,
    var results: List<NewsArticle>,
    var section: String,
    var status: String
)