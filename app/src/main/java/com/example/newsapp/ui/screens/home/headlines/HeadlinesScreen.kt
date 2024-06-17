package com.example.newsapp.ui.screens.home.headlines

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.components.BallPulseSyncIndicator
import com.example.newsapp.ui.components.MyBottomSheet
import com.example.newsapp.ui.components.NewsArticleItem
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch

const val TAG = "Headlines Screen"

/* TODO("Figure out why the lazycolumn causes it to crash) */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlinesScreen(
    homeScreenViewModel: HomeScreenViewModel,
    webViewViewModel:WebViewViewModel,
    navHostController: NavHostController,
) {
    val articles = homeScreenViewModel.topHeadlinesState.collectAsState()
    var selectedCategory by rememberSaveable {
        mutableStateOf(1)
    }
    var sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(sheetState.isVisible) }
    var chatHistory = homeScreenViewModel.chatHistoryState.collectAsState()
    val scope = rememberCoroutineScope()
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                },
                modifier = Modifier
                    .height(40.dp)
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background
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
        if (articles.value.isEmpty()){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(color = MaterialTheme.colorScheme.background),
            ){
                BallPulseSyncIndicator()
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
                            newsCategoryItem.id?.let { it1 ->
                                CategoryItem(
                                    category = newsCategoryItem.name,
                                    key = it1,
                                    selectedCategory = selectedCategory,
                                    onCategoryButtonClick = {
                                        selectedCategory = newsCategoryItem.id
                                        homeScreenViewModel.getCategoryTopStories(it)
                                    }
                                )
                            }
                        }
                    }
                }
                items(articles.value) { article ->
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
                            homeScreenViewModel.getArticleSummary(articleURL = article.htmlurl)
                            scope.launch {
                                sheetState.show()
                                showBottomSheet = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    key:Int,
    selectedCategory: Int,
    onCategoryButtonClick:(category: String)->Unit
) {
    val bgColor= if(!isSystemInDarkTheme()){
         animateColorAsState(
            targetValue = if (selectedCategory == key) Color.Black else Color.Transparent,
            animationSpec = tween(500, easing = LinearEasing),
            label = "category button animation"
        )
    } else {
        animateColorAsState(
            targetValue = if (selectedCategory == key) Constants.colors.get(1) else Color.Transparent,
            animationSpec = tween(500, easing = LinearEasing),
            label = "category button animation"
        )
    }
    Button(
        modifier = Modifier
            .padding(top = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor.value,
        ),
        onClick = {
            onCategoryButtonClick(category)
        }) {
        Text(
            text = category,
            color = if(selectedCategory == key) Color.White else MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
        )
    }
}
fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )
        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )
        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}

@Preview
@Composable
fun HeadlinesScreenPreview() {
    NewsAppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(color = Color.LightGray)
                    .height(120.dp)
                    .fillMaxWidth(0.4f)
                    .shimmerLoadingAnimation(
                        widthOfShadowBrush = 120
                    ) // <--- Here.
            )
        }
    }
}