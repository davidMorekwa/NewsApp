package com.example.newsapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.components.NewsArticleItem
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

const val TAG = "HOME SCREEN"
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    webViewViewModel: WebViewViewModel,
    navHostController: NavHostController,
    scrollState: LazyListState,
    categoryPosition: Float,
) {
    val localUriHandler = LocalUriHandler.current
    val newsArticles = homeScreenViewModel.state.collectAsState()
    val myObserver = Observer<Boolean> { newState ->
        newState
    }
    val scrollUpState = homeScreenViewModel.scrollUp.collectAsState()
    val isViewArticle by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedCategory by rememberSaveable {
        mutableStateOf(0)
    }
    val listState = rememberLazyListState()
    val flingbehaviour = rememberSnapFlingBehavior(lazyListState = listState)
    Scaffold(
        topBar = {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 2.dp),
                modifier = Modifier
                    .graphicsLayer { translationY = categoryPosition }
            ){
                items(Constants.listOfCategories.size){ index: Int ->
                    CategoryItem(
                        category = Constants.listOfCategories.get(index).name,
                        key = index,
                        selectedCategory = selectedCategory
                    ) {
                        homeScreenViewModel.getCategoryTopStories(it)
                        selectedCategory = index
                    }
                }
            }
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
                .padding(top = paddingTop)
        ) {
            if(newsArticles.value.isEmpty()){
                CircularProgressIndicator(
                    color = Color.Black
                )
            }else {
                NewsArticleList(
                    scrollState = scrollState,
                    newsArticles = newsArticles,
                    webViewViewModel = webViewViewModel,
                    navHostController = navHostController,
                    homeScreenViewModel = homeScreenViewModel,
                )
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


//@Preview
//@Composable
//fun CategoryItemPreview() {
//    NewsAppTheme {
//        CategoryItem(category = "Fashion")
//    }
//}

@Preview
@Composable
fun HomeScreenPreview() {
    NewsAppTheme(
        useDarkTheme = false
    ) {
//        HomeScreen()
    }
}