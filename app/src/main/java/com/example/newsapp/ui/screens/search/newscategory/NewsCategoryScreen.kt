package com.example.newsapp.ui.screens.search.newscategory

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.components.BallPulseSyncIndicator
import com.example.newsapp.ui.components.CircleShapeIndicator
import com.example.newsapp.ui.components.MyDropDownMenu
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.home.headlines.shimmerLoadingAnimation
import com.example.newsapp.ui.screens.search.SearchScreenViewModel
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

/*
This is the screen displayed when a user clicks on one
of the categories in the search screen
*/

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewsCategoryScreen(
    searchScreenViewModel: SearchScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    webViewViewModel: WebViewViewModel,
    navHostController: NavHostController
) {
    val articles = searchScreenViewModel.searchResultUiState.collectAsState()
    val isSearch = searchScreenViewModel.isSearch.collectAsState()
    val searchString = searchScreenViewModel.searchString.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if(isSearch.value){
                        Text(
                            text = "Results for '${searchString.value}'",
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Serif
                        )
                    } else {
                        Text(
                            text = searchString.value,
                            fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 12.dp)
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        if(articles.value.articles.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                BallPulseSyncIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                items(articles.value.articles) { article ->
                    CategoryScreenArticleItem(
                        article = article,
                        onAddToFavoriteClick = {
                            searchScreenViewModel.addToFavorites(article)
                        },
                        onAddTOBookmarkClick = {
                            searchScreenViewModel.addToBookmarks(article)
                        },
                        onArticleClick = {
                            webViewViewModel.readArticle(articleUrl = article.htmlurl)
                            navHostController.navigate(NavigationScreens.ARTICLE_VIEW_SCREEN.name)
                        },
                        onGeminisClick = {

                        }
                    )
                }
            }
        }
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)
//                .background(color = MaterialTheme.colorScheme.background)
//        ) {
//
//        }
    }
}

@Composable
fun CategoryScreenArticleItem(
    article: NewsArticle,
    onAddToFavoriteClick:()->Unit,
    onAddTOBookmarkClick:()->Unit,
    onArticleClick: ()->Unit,
    onGeminisClick: ()->Unit
) {
    val multimedia = article.multimedia
    var dropDownMenuExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var buttonSize by remember { mutableStateOf(Size.Zero) }
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onArticleClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(
                        if (multimedia.isNullOrEmpty()) 1f else 0.6f
                    )
                    .height(120.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    lineHeight = 15.sp
                )
                if (multimedia.isNullOrEmpty()) {
                    Text(
                        text = article.articleabstract,
                        fontSize = 12.sp
                    )
                }
                IconButton(
                    onClick = {
                        dropDownMenuExpanded = true
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_dots_45___),
                        contentDescription = "More",
                        tint = if (isSystemInDarkTheme()) Color.White else Color.DarkGray,
                    )
                    MyDropDownMenu(
                        buttonSize = buttonSize,
                        dropDownMenuExpanded = dropDownMenuExpanded,
                        dropDownMenuOnDismissRequest = {
                            dropDownMenuExpanded = false
                        },
                        onAddToBookmarkClick = { onAddTOBookmarkClick() },
                        onAddToFavoriteClick = { onAddToFavoriteClick() },
                        onGeminisClick = { onGeminisClick() }
                    )

                }
            }
            if (!multimedia.isNullOrEmpty()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                ) {
                    SubcomposeAsyncImage(
                        model = multimedia?.get(0)?.url,
                        contentDescription = "Article Multimedia",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(120.dp)
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .background(color = Color.LightGray)
                                    .height(120.dp)
                                    .fillMaxWidth()
                                    .shimmerLoadingAnimation()
                            )

                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun MyPreview() {
    NewsAppTheme() {
        CategoryScreenArticleItem(article = Constants.sampleArticle,
            onAddToFavoriteClick = { /*TODO*/ },
            onAddTOBookmarkClick = { },
            onArticleClick = {},
            onGeminisClick = {}
        )
    }
}