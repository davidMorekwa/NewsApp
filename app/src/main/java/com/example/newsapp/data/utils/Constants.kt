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
    val colors = listOf<Color>(
        Color(0xFF006874),
        Color(0xFF324B4F),
        Color(0xFF95B0B5),
        Color(0xFF88477A),
        Color(0xFF00A9CA),
        Color(0xFF2F4858)
    )
    val listOfCategories: MutableList<NewsCategoryItem> = mutableListOf(
        NewsCategoryItem(
            id = 1,
            name = "Arts",
            imageURl = "https://img.freepik.com/free-photo/modern-watercolor-background-with-abstract-design_23-2147923076.jpg?w=1800&t=st=1717693145~exp=1717693745~hmac=ae6664519651e54f843dc07666102c396539e9fb24eb384264cb76f1f6e26380"
        ),
        NewsCategoryItem(
            id = 2,
            name = "Automobile",
            imageURl = "https://img.freepik.com/free-photo/view-3d-car-sketch-style_23-2151139034.jpg?t=st=1717693806~exp=1717697406~hmac=7057997d2966d9c7bd0664cb1560812400ca3f61a016cbbe89a6cf62dcb74e0f&w=2000"
        ),
        NewsCategoryItem(3, "Book/Review", "https://img.freepik.com/free-vector/hand-drawn-flat-design-stack-books-illustration_23-2149330601.jpg?t=st=1717693861~exp=1717697461~hmac=0f3653a59f218fde2ccb1c5617e248a3cd7bcb9a3a47a8a967aa76f7e1ef2eec&w=1380"),
        NewsCategoryItem(4, "Business", "https://img.freepik.com/free-vector/business-woman-working-making-notes_1262-21212.jpg?t=st=1717694039~exp=1717697639~hmac=9e4b546e541b4fe23a4dd1fb4ff158b7b779c988d0edd842e9a8c912f9266612&w=2000"),
        NewsCategoryItem(5, "Fashion", "https://img.freepik.com/free-vector/abstract-hand-drawn-group-women_52683-58046.jpg?t=st=1717745794~exp=1717749394~hmac=d52bef737d02618c54f853f2c910fe8567b1ba27ad29e5c32c61ad9438a9b254&w=1380"),
        NewsCategoryItem(6, "Food", "https://img.freepik.com/free-vector/hand-drawn-fast-food-frame_23-2147854833.jpg?t=st=1717745844~exp=1717749444~hmac=e971df5144f298ccc7233a2e4c9f4e450802d0152fd8b9c8a2b1b4b753dde16c&w=1380"),
        NewsCategoryItem(7, "Health", "https://img.freepik.com/free-vector/patient-taking-medical-examination_23-2148857674.jpg?t=st=1717745897~exp=1717749497~hmac=39c24d3a2cff29a87e6668a180b86a470230042f4c576c546f2b0ffcb6c6de29&w=2000"),
        NewsCategoryItem(8, "Home", "https://img.freepik.com/free-vector/creative-house-cross-section_23-2148670996.jpg?t=st=1717746109~exp=1717749709~hmac=1c33c6797895e03e1b30805b8c89824bee7cf9c1cb719d70a17b4ff5d581515b&w=1380"),
        NewsCategoryItem(9, "Insider", ""),
        NewsCategoryItem(10, "Magazine", "https://img.freepik.com/free-psd/fashion-catalog-template-design_23-2151010988.jpg?t=st=1717746174~exp=1717749774~hmac=939e2b30cd04b8bf3447ca1f0d07fc621b6afa372e29ae2ea790b9b7953ff983&w=1380"),
        NewsCategoryItem(11, "Movies", "https://img.freepik.com/free-vector/film-rolls-concept-illustration_114360-6394.jpg?t=st=1717746213~exp=1717749813~hmac=5127cbfdef9e444fe99a32edb826acc2ea75c2d4538eec2663d071a5b7e0dec5&w=1380"),
        NewsCategoryItem(12, "Opinion", ""),
        NewsCategoryItem(13, "Politics",""),
        NewsCategoryItem(14, "Real Estate", "https://img.freepik.com/free-vector/hand-drawn-shortage-affordable-housing-illustration_23-2150801661.jpg?t=st=1717746324~exp=1717749924~hmac=047cd4c99d4bd613c72452c565f99ba4f4f939dbf8d97d145e6dd904d4232b46&w=1480"),
        NewsCategoryItem(15, "Science","https://img.freepik.com/free-vector/science-lab-objects_23-2148488312.jpg?t=st=1717746363~exp=1717749963~hmac=f84821725d1ade3a10ad46ac5c3209e9de8679873e4f70623f400296947a617b&w=1380"),
        NewsCategoryItem(16, "Sports","https://cdn-icons-png.freepik.com/256/5351/5351486.png?semt=ais_hybrid"),
        NewsCategoryItem(17, "Technology","https://img.freepik.com/premium-vector/internet-network-communication-web-technology-computer-icon-vector-online-website-design_1013341-38132.jpg?w=1380"),
        NewsCategoryItem(18, "Travel", ""),
        NewsCategoryItem(19, "US",""),
        NewsCategoryItem(20, "World",""),

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
    val STORED_THEME = booleanPreferencesKey("isDarkTheme")
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