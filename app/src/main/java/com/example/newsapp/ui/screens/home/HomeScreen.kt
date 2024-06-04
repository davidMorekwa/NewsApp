package com.example.newsapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.ui.components.HeadlineNewsArticle
import com.example.newsapp.ui.components.NewsArticleItem
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

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
    scrollState: LazyListState,
    categoryPosition: Float,
) {
    val headlineNewsArticles = homeScreenViewModel.topHeadlinesState.collectAsState()
    val latestNewsArticles = homeScreenViewModel.latestNewsState.collectAsState().value
    val isRefreshing = homeScreenViewModel.isRefereshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing.value, { homeScreenViewModel.refresh() })
    val scrollState2 = rememberScrollState()
    val viewTopHeadlines by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedCategory by rememberSaveable {
        mutableStateOf(0)
    }
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
                    .graphicsLayer {
                        translationX = categoryPosition
                    }
            )
        }
    ) {
        val paddingTop by animateDpAsState(
            targetValue = if(categoryPosition == -150f) 0.dp else 45.dp,
            label = "Padding Top",
            animationSpec = tween(50, easing = EaseInOutQuart)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .pullRefresh(pullRefreshState)
        ) {
            PullRefreshIndicator(
                refreshing = isRefreshing.value,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(5f)
            )

            if(headlineNewsArticles.value.isEmpty()){
                CircularProgressIndicator(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Button(
                            onClick = {
//                                navHostController.navigate(NavigationScreens.HEADLINE_ARTICLES_SCREEN.name)
                                      /* TODO(Navigate to the Headlines Screen) */
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = if(isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(1.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
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
                            }
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun NewsArticleList(
    scrollState: LazyListState,
    newsArticles: State<List<NewsArticle>>,
    webViewViewModel: WebViewViewModel,
    navHostController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
) {
    var selectedCategory: Int by rememberSaveable {
        mutableStateOf(0)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
            state = scrollState,
        ) {
            items(newsArticles.value) { article: NewsArticle ->
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
                    }
                )
            }
        }
    }

}






//@Preview
//@Composable
//fun CategoryItemPreview() {
//    NewsAppTheme {
//        CategoryItem(category = "Fashion")
//    }
//}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NewsAppTheme(
        useDarkTheme = false
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){

        }

    }
}