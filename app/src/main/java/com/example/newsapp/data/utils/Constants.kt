package com.example.newsapp.data.utils

import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.newsapp.BuildConfig.API_KEY
import com.example.newsapp.data.model.NewsCategoryItem

object Constants {
    const val key = API_KEY

    const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    const val DEFAULT_PAGE_SIZE = 10
    val categoryNames = listOf<String>(
        "Headlines","Arts", "Automobile", "Book/Review", "Business", "Fashion", "Food", "Health", "Home", "Insider", "Magazine", "Movies", "Opinion", "Politics", "Real Estate", "Science", "Sports", "Technology", "Travel", "US", "World"
    )
    val colors = listOf<Color>(
        Color(0xFF006874),
        Color(0xFF324B4F),
        Color(0xFF95B0B5),
        Color(0xFF88477A),
        Color(0xFF00A9CA),
        Color(0xFF2F4858)
    )
    val listOfCategories: MutableList<NewsCategoryItem> = mutableListOf()
    val newsCategories = categoryNames.forEachIndexed { index, name ->
        listOfCategories.add(NewsCategoryItem(name = name, id = index))

    }
    // Room DB
    const val DATABASE_NAME = "news_article.db"
    const val TABLE_NEWS_ARTICLES = "tbl_news_articles"
    const val TABLE_MULTIMEDIA = "tbl_multimedia"
    const val TABLE_ARTICLE_AND_MEDIA = "tbl_article_and_media"
    val STORED_THEME = booleanPreferencesKey("isDarkTheme")
}