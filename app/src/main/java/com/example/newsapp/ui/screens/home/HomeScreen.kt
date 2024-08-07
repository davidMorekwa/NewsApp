package com.example.newsapp.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.ui.components.BallPulseSyncIndicator
import com.example.newsapp.ui.components.HeadlineNewsArticle
import com.example.newsapp.ui.components.MyBottomSheet
import com.example.newsapp.ui.components.NewsArticleItem
import com.example.newsapp.ui.components.WeatherComponent
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

const val TAG = "HOME SCREEN"
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    webViewViewModel: WebViewViewModel,
    navHostController: NavHostController,
) {
    val headlineNewsArticles = homeScreenViewModel.topHeadlinesState.collectAsState()
    val latestNewsArticles = homeScreenViewModel.latestNewsState.collectAsState().value
    val isRefreshing = homeScreenViewModel.isRefereshing.collectAsState()
    var chatHistory = homeScreenViewModel.chatHistoryState.collectAsState()
    var weather = homeScreenViewModel.weatherState.collectAsState()
    val selectedCategories = homeScreenViewModel.selectedCategories.collectAsState()
    var selectedCategoryNewsListState = homeScreenViewModel.selectedCategoryNewsListState.collectAsState()
    var categoryIndex = homeScreenViewModel.categoryIndex.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing.value, { homeScreenViewModel.refresh() })

    var sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(sheetState.isVisible) }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var articleCount = homeScreenViewModel.articleCount.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "News",
                        fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
                    )
                },
                modifier = Modifier
                    .height(40.dp)
            )
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 8.dp, end = 8.dp)
                .pullRefresh(pullRefreshState)
        ) {
            if(showBottomSheet) {
                MyBottomSheet(
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    },
                    sheetState = sheetState,
                    chatHistory = chatHistory.value,
                    onSendClick = {
                        homeScreenViewModel.sendMessage(message = it)
                        scope.launch {
                            sheetState.show()
                            showBottomSheet = true
                        }
                    }
                )
            }
            PullRefreshIndicator(
                refreshing = isRefreshing.value,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(5f)
            )
//            LaunchedEffect(isRefreshing.value) {
//                if (!isRefreshing.value) {
//                    val newArticlesCount = latestNewsArticles.size
//                    if (newArticlesCount == previousArticlesCount.intValue) {
//                        Toast.makeText(context, "No new articles available", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
////                    previousArticlesCount.intValue = newArticlesCount
//            }

            if(headlineNewsArticles.value.isEmpty()){
                BallPulseSyncIndicator()
            }else {
                LaunchedEffect(key1 = lazyListState) {
                    snapshotFlow { lazyListState.firstVisibleItemIndex }
                        .collect { index ->
                            Log.d(TAG, "Index: $index")
                            Log.d(TAG, "Article count $articleCount")
                            if (index >= articleCount.value - 7) {
                                Log.d(TAG, "New Index: $categoryIndex")
                                homeScreenViewModel.fetchOtherArticles(categoryIndex.value)
                            }
                        }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState
                ) {
                    val date = LocalDate.now()
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "${date.dayOfWeek}, ${date.dayOfMonth} ${date.month}",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Light,
                            )
                            if (weather.value is WeatherUiState.Success) {
                                WeatherComponent(
                                    weatherResponse = (weather.value as WeatherUiState.Success).weather
                                )
                            }
                        }
                        Button(
                            onClick = {
                                navHostController.navigate(NavigationScreens.HEADLINE_ARTICLES_SCREEN.name)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = if(isSystemInDarkTheme()) Color.White else Color.Black
                            ),
                            modifier = Modifier
                                .padding(0.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(1.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)

                            ) {

                                Text(
                                    text = "Top Headlines",
                                    fontFamily = FontFamily(Font(R.font.delagothic_one_regular)),
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "More",
                                )
                            }
                        }
                        HeadlineNewsArticle(newsArticles = headlineNewsArticles.value.take(7))
                        Text(
                            text = "Latest News",
                            fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
                        )

                    }
                    items(latestNewsArticles) { article: NewsArticle ->
                        NewsArticleItem(
                            article = article,
                            onArticleClick = {
                                webViewViewModel.readArticle(articleUrl = article.htmlurl)
                                navHostController.navigate(NavigationScreens.ARTICLE_VIEW_SCREEN.name)
                            },
                            onAddToFavoriteClick = {
                                homeScreenViewModel.addToFavorites(article = article)
                            },
                            onAddToBookmarkClick = {
                                homeScreenViewModel.addToBookmarks(article = article)
                            },
                            onGeminisClick = {
                                scope.launch {
                                    showBottomSheet = true
                                    homeScreenViewModel.getArticleSummary(articleURL = article.htmlurl)
                                }
                            },
                        )

                    }
                    if (selectedCategoryNewsListState.value is CategoryNewsUiState.Success){
                        for((key, value) in (selectedCategoryNewsListState.value as CategoryNewsUiState.Success).articles){
                            if(value.isNotEmpty()){
                                item {
                                    Text(
                                        text = key,
                                        fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
                                    )
                                }
                                items(value){ article ->
                                    NewsArticleItem(
                                        article = article,
                                        onArticleClick = {
                                            webViewViewModel.readArticle(articleUrl = article.htmlurl)
                                            navHostController.navigate(NavigationScreens.ARTICLE_VIEW_SCREEN.name)
                                        },
                                        onAddToFavoriteClick = {
                                            homeScreenViewModel.addToFavorites(article = article)
                                        },
                                        onAddToBookmarkClick = {
                                            homeScreenViewModel.addToBookmarks(article = article)
                                        },
                                        onGeminisClick = {
                                            scope.launch {
                                                showBottomSheet = true
                                                homeScreenViewModel.getArticleSummary(articleURL = article.htmlurl)
                                            }
                                        },
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NewsAppTheme(
        useDarkTheme = false
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ){

        }

    }
}