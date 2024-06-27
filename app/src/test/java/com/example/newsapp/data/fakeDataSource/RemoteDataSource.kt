package com.example.newsapp.data.fakeDataSource

import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.response.weather.Main
import com.example.newsapp.data.model.response.weather.Weather
import com.example.newsapp.data.model.response.weather.WeatherResponse
import com.example.newsapp.data.repositories.local_data.entities.NewsCategoryEntity
import java.util.UUID

object RemoteDataSource {
    //    val newsNewsSources = listOf<NewsSource>(
//        NewsSource(
//            id = "1",
//            name = "CNN"
//        ),
//        NewsSource(
//            id = "2",
//            name = "BBC"
//        ),
//        NewsSource(
//            id = "3",
//            name = "WSJ"
//        ),
//        NewsSource(
//            id = "4",
//            name = "AlJazeera"
//        ),
//    )
    val weather = WeatherResponse(
        weather = listOf(
            Weather(
                id = 1,
                main = "Sunny",
                description = "Clear",
                icon = "01d"
            )
        ),
        name = "New York",
        main = Main(temp = 23.0)
    )
    val newsArticles = listOf<NewsArticle>(
        NewsArticle(
            articleabstract = "Abstract 1",
            byline = "Byline 1",
            itemType = "itemtype 1",
            multimedia = null,
            publishedDate = "Date 1",
            section = "Section 1",
            subsection = "Subsection 1",
            title = "Title 1",
            nyturi = "uri 1",
            htmlurl = "url 1"
        ),
        NewsArticle(
            articleabstract = "Abstract 2",
            byline = "Byline 2",
            itemType = "itemtype 2",
            multimedia = null,
            publishedDate = "Date 2",
            section = "Section 2",
            subsection = "Subsection 2",
            title = "Title 2",
            nyturi = "uri 2",
            htmlurl = "url 2"
        ),
        NewsArticle(
            articleabstract = "Abstract 3",
            byline = "Byline 3",
            itemType = "itemtype 3",
            multimedia = null,
            publishedDate = "Date 3",
            section = "Section 3",
            subsection = "Subsection 3",
            title = "Title 3",
            nyturi = "uri 3",
            htmlurl = "url 3"
        ),
        NewsArticle(
            articleabstract = "Abstract 4",
            byline = "Byline 4",
            itemType = "itemtype 4",
            multimedia = null,
            publishedDate = "Date 4",
            section = "Section 4",
            subsection = "Subsection 4",
            title = "Title 4",
            nyturi = "uri 4",
            htmlurl = "url 4"
        ),
        NewsArticle(
            articleabstract = "Abstract 5",
            byline = "Byline 5",
            itemType = "itemtype 5",
            multimedia = null,
            publishedDate = "Date 5",
            section = "Section 5",
            subsection = "Subsection 5",
            title = "Title 5",
            nyturi = "uri 5",
            htmlurl = "url 5"
        ),
        NewsArticle(
            articleabstract = "Abstract 6",
            byline = "Byline 6",
            itemType = "itemtype 6",
            multimedia = null,
            publishedDate = "Date 6",
            section = "Section 6",
            subsection = "Subsection 6",
            title = "Title 6",
            nyturi = "uri 6",
            htmlurl = "url 6"
        ),
        NewsArticle(
            articleabstract = "Abstract 7",
            byline = "Byline 7",
            itemType = "itemtype 7",
            multimedia = null,
            publishedDate = "Date 7",
            section = "Section 7",
            subsection = "Subsection 7",
            title = "Title 7",
            nyturi = "uri 7",
            htmlurl = "url 7"
        )
    )
    val latestNewsArticles = listOf<NewsArticle>(
        NewsArticle(
            articleabstract = "Abstract 1",
            byline = "Byline 1",
            itemType = "itemtype 1",
            multimedia = null,
            publishedDate = "Date 1",
            section = "Section 1",
            subsection = "Subsection 1",
            title = "Title 1",
            nyturi = "uri 1",
            htmlurl = "url 1"
        ),
        NewsArticle(
            articleabstract = "Abstract 2",
            byline = "Byline 2",
            itemType = "itemtype 2",
            multimedia = null,
            publishedDate = "Date 2",
            section = "Section 2",
            subsection = "Subsection 2",
            title = "Title 2",
            nyturi = "uri 2",
            htmlurl = "url 2"
        ),
        NewsArticle(
            articleabstract = "Abstract 3",
            byline = "Byline 3",
            itemType = "itemtype 3",
            multimedia = null,
            publishedDate = "Date 3",
            section = "Section 3",
            subsection = "Subsection 3",
            title = "Title 3",
            nyturi = "uri 3",
            htmlurl = "url 3"
        ),
        NewsArticle(
            articleabstract = "Abstract 4",
            byline = "Byline 4",
            itemType = "itemtype 4",
            multimedia = null,
            publishedDate = "Date 4",
            section = "Section 4",
            subsection = "Subsection 4",
            title = "Title 4",
            nyturi = "uri 4",
            htmlurl = "url 4"
        ),
        NewsArticle(
            articleabstract = "Abstract 5",
            byline = "Byline 5",
            itemType = "itemtype 5",
            multimedia = null,
            publishedDate = "Date 5",
            section = "Section 5",
            subsection = "Subsection 5",
            title = "Title 5",
            nyturi = "uri 5",
            htmlurl = "url 5"
        ),
    )
    val categories = listOf(
        NewsCategoryEntity(UUID.randomUUID(), "Section 1"),
        NewsCategoryEntity(UUID.randomUUID(), "Section 2"),
        NewsCategoryEntity(UUID.randomUUID(), "Section 3"),
//        NewsCategoryEntity(UUID.randomUUID(), "Section 4"),
//        NewsCategoryEntity(UUID.randomUUID(), "Section 5"),
    )
}