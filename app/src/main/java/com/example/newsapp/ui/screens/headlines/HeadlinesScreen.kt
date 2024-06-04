package com.example.newsapp.ui.screens.headlines

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.fillMaxSize
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.components.NewsArticleItem
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

const val TAG = "Headlines Screen"

/* TODO("Figure out why the lazycolumn causes it to crash) */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlinesScreen(
    homeScreenViewModel: HomeScreenViewModel,
    webViewViewModel:WebViewViewModel,
    navHostController: NavHostController,
) {
    val articles = homeScreenViewModel.topHeadlinesState.collectAsState()
    Log.d(TAG, "Articles size: ${articles.value.size}")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Headlines",
                        fontFamily = FontFamily(Font(R.font.delagothic_one_regular)),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier
                    .height(40.dp)
                    .border(1.dp, Color.Red)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ){
            if (articles.value.isEmpty()){
                Box(){
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn() {
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(Constants.listOfCategories) { newsCategoryItem: NewsCategoryItem ->
                                CategoryItem(
                                    category = newsCategoryItem.name,
                                    key = newsCategoryItem.id,
                                    selectedCategory = 0,
                                    onCategoryButtonClick = {
                                        homeScreenViewModel.getCategoryTopStories(it)
                                    }
                                )
                            }
                        }
                    }
                    items(articles.value) { article ->
                        Text(text = article.title)
//                    NewsArticleItem(
//                        article = article,
//                        onArticleClick = {
//                            webViewViewModel.readArticle(articleUrl = article.htmlurl)
//                            navHostController.navigate(NavigationScreens.ARTICLE_VIEW_SCREEN.name)
//                        },
//                        onAddToFavoriteClick = {
//                            homeScreenViewModel.addToFavorites(article = article)
//                        },
//                        onAddToBookmarkClick = {
//                            homeScreenViewModel.addToBookmarks(article = article)
//                        }
//                    )
                    }
                }
            }
        }
//        LazyColumn(
//            modifier = GlanceModifier.fillMaxSize()
//        ){
//            item {
//                LazyRow(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(4.dp),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    items(Constants.listOfCategories){ newsCategoryItem: NewsCategoryItem ->
//                        CategoryItem(
//                            category = newsCategoryItem.name,
//                            key = newsCategoryItem.id,
//                            selectedCategory = 0,
//                            onCategoryButtonClick = {
//                                homeScreenViewModel.getCategoryTopStories(it)
//                            }
//                        )
//                    }
//                }
//            }
//            items(articles.value){ article ->
//                NewsArticleItem(
//                    article = article,
//                    onArticleClick = {
//                        webViewViewModel.readArticle(articleUrl = article.htmlurl)
//                        navHostController.navigate(NavigationScreens.ARTICLE_VIEW_SCREEN.name)
//                    },
//                    onAddToFavoriteClick = {
//                        homeScreenViewModel.addToFavorites(article = article)
//                    },
//                    onAddToBookmarkClick = {
//                        homeScreenViewModel.addToBookmarks(article = article)
//                    }
//                )
//            }
//        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    key:Int,
    selectedCategory: Int,
    onCategoryButtonClick:(category: String)->Unit
) {
    if(!isSystemInDarkTheme()){
        val bgColor by animateColorAsState(
            targetValue = if (selectedCategory == key) Color.White else Color.Transparent,
            animationSpec = tween(500, easing = LinearEasing),
            label = "category button animation"
        )
    }
    val bgColor: Color by animateColorAsState(
        targetValue = if (selectedCategory == key) Color.Black else Color.Transparent,
        animationSpec = tween(500, easing = LinearEasing),
        label = "category button animation"
    )
    Button(
        modifier = Modifier
            .padding(top = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor
        ),
        onClick = {
//            selected = !selected
            onCategoryButtonClick(category)
        }) {
        Text(
            text = category,
            color = if (selectedCategory == key) Color.White else Color.Black,
            fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
        )
    }
}

@Preview
@Composable
fun HeadlinesScreenPreview() {
    NewsAppTheme {


    }
}