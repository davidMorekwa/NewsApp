package com.example.newsapp.data.utils

import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.newsapp.BuildConfig.API_KEY
import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.NewsCategoryItem

object Constants {
    const val key = API_KEY

    const val TOP_HEADLINES_BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    const val LATEST_NEWS_BASE_URL = "https://api.nytimes.com/svc/news/v3/content/nyt/"
    const val DEFAULT_PAGE_SIZE = 10
    val categoryNames = listOf<String>(
        "All","Arts", "Automobile", "Book/Review", "Business", "Fashion", "Food", "Health", "Home", "Insider", "Magazine", "Movies", "Opinion", "Politics", "Real Estate", "Science", "Sports", "Technology", "Travel", "US", "World"
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
    const val TABLE_RECENT_SEARCH = "tbl_recent_search"
    val STORED_THEME = booleanPreferencesKey("isDarkTheme")
    val sampleArticle = NewsArticle(
        articleabstract = "In a groundbreaking discovery, scientists have uncovered a new species of dinosaur that roamed the earth millions of years ago.",
        byline = "By John Doe",
        itemType = "Article",
        multimedia = listOf(
            Multimedia(
                url = "https://static01.nyt.com/images/2024/05/26/business/26Bignumber-illo/26Bignumber-illo-articleInline.jpg",
                format = "superJumbo",
                height = 1200,
                width = 1600,
                type = "image",
                subtype = "photo",
                caption = "The newly discovered species of dinosaur, believed to be one of the largest ever found.",
                copyright = "sample copyright"

//                    url= "https://static01.nyt.com/images/2024/05/26/business/26Bignumber-illo/26Bignumber-illo-mediumThreeByTwo210.jpg",
//                    format= "mediumThreeByTwo210",
//                    height= 400,
//                    width= 600,
//                    type= "image",
//                    subtype= "photo",
//                    caption= "",
//                    copyright= "Dominic Kesterton"

            )
        ),
        publishedDate = "2024-05-24",
        section = "Science",
        subsection = "Paleontology",
        title = "New Species of Dinosaur Discovered In Kenya Nairobi in Africa and they are even in Europe, Asia, North America and South America as well. Did i mention they are also in Australia and New Zealand?",
        nyturi = "nyt://article/12345678-90ab-cdef-1234-567890abcdef",
        htmlurl = "https://www.nytimes.com/2024/05/24/science/new-dinosaur-species.html"
    )
    val finalArticle = NewsArticle(
        articleabstract = "",
        byline = "",
        itemType = "",
        multimedia = listOf(
            Multimedia(
                url = "https://static01.nyt.com/images/2024/05/26/business/26Bignumber-illo/26Bignumber-illo-articleInline.jpg",
                format = "superJumbo",
                height = 400,
                width = 600,
                type = "image",
                subtype = "photo",
                caption = "The newly discovered species of dinosaur, believed to be one of the largest ever found.",
                copyright = "sample copyright"
            )
        ),
        publishedDate = "",
        section = "",
        subsection = "",
        title = "",
        nyturi = "",
        htmlurl = ""
    )

    val listOfNewsarticles = listOf<NewsArticle>(sampleArticle, sampleArticle, sampleArticle, sampleArticle, finalArticle)
}