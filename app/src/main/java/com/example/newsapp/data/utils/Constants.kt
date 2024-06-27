package com.example.newsapp.data.utils

import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.newsapp.BuildConfig.API_KEY
import com.example.newsapp.BuildConfig.WEATHER_API_KEY
import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.NewsCategoryItem

object Constants {
    const val nyt_key = API_KEY
    const val weather_key = WEATHER_API_KEY

    const val TOP_HEADLINES_BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    const val LATEST_NEWS_BASE_URL = "https://api.nytimes.com/svc/news/v3/content/nyt/"
    const val DEFAULT_PAGE_SIZE = 10
    val colors = listOf<Color>(
        Color(0xFF006874),
        Color(0xFF324B4F),
        Color(0xFF95B0B5),
        Color(0xFF88477A),
        Color(0xFF00A9CA),
        Color(0xFF2F4858)
    )
    val listOfCategories: MutableList<NewsCategoryItem> = mutableListOf(
        NewsCategoryItem(1, "Arts",),
        NewsCategoryItem(2, "Automobile",),
        NewsCategoryItem(3, "Book/Review"),
        NewsCategoryItem(4, "Business"),
        NewsCategoryItem(5, "Fashion"),
        NewsCategoryItem(6, "Food"),
        NewsCategoryItem(7, "Health"),
        NewsCategoryItem(8, "Home"),
        NewsCategoryItem(9, "Insider"),
        NewsCategoryItem(10, "Magazine"),
        NewsCategoryItem(11, "Movies"),
        NewsCategoryItem(12, "Opinion"),
        NewsCategoryItem(13, "Politics"),
        NewsCategoryItem(14, "Real Estate"),
        NewsCategoryItem(15, "Science"),
        NewsCategoryItem(16, "Sports"),
        NewsCategoryItem(17, "Technology"),
        NewsCategoryItem(18, "Travel"),
        NewsCategoryItem(19, "US"),
        NewsCategoryItem(20, "World"),

    )
//    val newsCategories = categoryNames.forEachIndexed { index, name ->
//        listOfCategories.add(NewsCategoryItem(name = name, id = index, imageURl = "https://cdn-icons-png.freepik.com/256/5351/5351486.png?semt=ais_hybrid"))
//
//    }
    // Room DB
    const val DATABASE_NAME = "news_article.db"
    const val TABLE_NEWS_ARTICLES = "tbl_news_articles"
    const val TABLE_MULTIMEDIA = "tbl_multimedia"
    const val TABLE_ARTICLE_AND_MEDIA = "tbl_article_and_media"
    const val TABLE_RECENT_SEARCH = "tbl_recent_search"
    const val TABLE_NEWS_CATEGORY = "tbl_news_category"



    val STORED_THEME = booleanPreferencesKey("isDarkTheme")
    val IS_ONBOARDING_COMPLETE = booleanPreferencesKey("is_onboarding_complete")
    val sampleArticle = NewsArticle(
        articleabstract = "In a groundbreaking discovery, scientists have uncovered a new species of dinosaur that roamed the earth millions of years ago.",
        byline = "By John Doe",
        itemType = "Article",
        multimedia = listOf(
            Multimedia(
                url = "https://static01.nyt.com/images/2024/06/05/multimedia/05dc-terror-zhgl/05dc-terror-zhgl-thumbStandard.jpg",
                format = "superJumbo",
                height = 75,
                width = 75,
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
        title = "New Species of Dinosaur Discovered In Kenya Nairobi in Africa ",
        nyturi = "nyt://article/12345678-90ab-cdef-1234-567890abcdef",
        htmlurl = "https://www.nytimes.com/2024/05/24/science/new-dinosaur-species.html"
    )
    val finalArticle = NewsArticle(
        articleabstract = "",
        byline = "",
        itemType = "",
        multimedia = listOf(
            Multimedia(
                url = "https://static01.nyt.com/images/2024/06/05/multimedia/05dc-terror-zhgl/05dc-terror-zhgl-thumbStandard.jpg",
                format = "superJumbo",
                height = 75,
                width = 75,
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