package com.example.newsapp.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.data.model.NewsArticle
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.webview.WebViewViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel,
    webViewViewModel: WebViewViewModel,
    navHostController: NavHostController
) {
    var isSearch = searchScreenViewModel.isSearch.collectAsState()
    var uiState = searchScreenViewModel.uiState.collectAsState()
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    val scrollUpState = searchScreenViewModel.scrollUp.collectAsState()
    val searchBarPosition by animateFloatAsState(if (scrollUpState.value) -150f else 0f)
    var scrollState = rememberLazyGridState()
    var isTextFieldFocused: Boolean by rememberSaveable {
        mutableStateOf(true)
    }
    var recentSearch = searchScreenViewModel.recentSearch.collectAsState()
    searchScreenViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = {
                        println("Textfield: ${isTextFieldFocused}")
                        if(isSearch.value){
                            searchScreenViewModel.setIsSearch(false)
                        } else if(isTextFieldFocused){
                            isTextFieldFocused = !isTextFieldFocused
//                            println("Free focus: ${focusRequester.freeFocus()}")
                        } else {
                            navHostController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.height(39.dp)
            )
        }
    ) {
        if (!isSearch.value) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 25.dp)
                    .scrollable(scrollState, orientation = Orientation.Vertical)
            ) {
                OutlinedTextField(
                    value = searchValue,
                    onValueChange = {
                        println(it)
                        searchValue = it
                    },
                    placeholder = {
                        Text(text = "Search...")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        if(isSearch.value) {
                            IconButton(
                                onClick = {
                                    searchValue = ""
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            searchScreenViewModel.searchArticle(query = searchValue)
                            println("Search ${searchValue}")
                        }
                    ),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .graphicsLayer { translationY = searchBarPosition }
                        .onFocusChanged { it ->
                            isTextFieldFocused = !isTextFieldFocused
                        }

                )
                Spacer(modifier = Modifier.height(10.dp))
                if(!isTextFieldFocused) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                    ) {
                        items(
                            count = Constants.listOfCategories.size,
                        ) { index: Int ->
                            val color = Constants.colors[index.mod(Constants.colors.size)]
                            CategoryItem(
                                category = Constants.listOfCategories[index],
                                color = color,
                                onCategoryClick = {
                                    searchScreenViewModel.categoryHeadline(it.name)
                                }
                            )
                        }
                    }
                } else {
                    LazyColumn(){
                        items(recentSearch.value){ item: RecentSearchEntity ->
                            RecentSearchItem(
                                string = item.string,
                                onDeleteRecentSearch = {
                                    searchScreenViewModel.deleteSearchTerm(it)
                                },
                                onRecentSearchClick = {
                                    searchScreenViewModel.searchArticle(it)
                                }
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn(){
                items(uiState.value.articles){ article: NewsArticle ->  
                    SearchScreenArticleItem(
                        article = article,
                        onArticleClick = {
                            webViewViewModel.readArticle(articleUrl = article.htmlurl)
                            navHostController.navigate(NavigationScreens.ARTICLE_VIEW_SCREEN.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: NewsCategoryItem, color: Color, onCategoryClick: (category: NewsCategoryItem)-> Unit) {
    Box(
//        shadowElevation = 4.dp,
//        color = color,
        modifier = Modifier
            .width(70.dp)
            .height(100.dp)
            .padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(5.dp))
            .clickable {
                println("Cateegory ${category.name} clicked")
                onCategoryClick(category)
            }
            .background(color = color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 8.dp)
        ) {
            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp,
                maxLines = 2,
                )
        }

    }
}

@Composable
fun RecentSearchItem(
    string: String,
    onDeleteRecentSearch: (string: String)->Unit,
    onRecentSearchClick: (string: String)->Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onRecentSearchClick(string) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = string,
                fontWeight = FontWeight.Light
            )
            IconButton(
                onClick = {
                    onDeleteRecentSearch(string)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Delete search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}


@Preview
@Composable
fun SearchScreenPreview() {
    NewsAppTheme {
//        SearchScreen()
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

        }

        LazyColumn {
            items(Constants.listOfCategories.size){ index: Int ->
                CategoryItem(category = Constants.listOfCategories.get(index), color = Constants.colors[index.mod(Constants.colors.size)], onCategoryClick = { println("Category: ${Constants.listOfCategories.get(index)}") })
            }
        }
    }
}