package com.example.newsapp.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.repositories.local.entities.RecentSearchEntity
import com.example.newsapp.data.utils.Constants
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
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    var scrollState = rememberLazyGridState()
    var isTextFieldFocused: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var recentSearch = searchScreenViewModel.recentSearch.collectAsState()
    val scrollUpState = searchScreenViewModel.scrollUp.collectAsState()
    val position by animateFloatAsState(if (scrollUpState.value) -150f else 0f, tween(500, easing = EaseInOutQuart))
    val padding by animateDpAsState(targetValue = if (scrollUpState.value) 2.dp else 40.dp, tween(500, easing = EaseInOutQuart))
    val cornerRadius by animateDpAsState(targetValue = if (scrollUpState.value) 12.dp else 25.dp, tween(500, easing = EaseInOutQuart))
    searchScreenViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Search",
                        fontFamily = FontFamily(Font(R.font.delagothic_one_regular))
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        println("Textfield: ${isTextFieldFocused}")
                        if(isTextFieldFocused){
                            isTextFieldFocused = !isTextFieldFocused
                        } else {
                            navHostController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier
                    .height(39.dp)
                    .graphicsLayer {
                        translationY = position
                    }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding, bottom = 25.dp)
        ) {
            OutlinedTextField(
                value = searchValue,
                onValueChange = {
                    println(it)
                    searchValue = it
                    isTextFieldFocused = true
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface
                ),
                placeholder = {
                    Text(text = "Search...")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchScreenViewModel.searchArticle(query = searchValue)
                        println("Search ${searchValue}")
                        navHostController.navigate(NavigationScreens.SEARCH_RESULT_SCREEN.name)
                    },
                ),
                shape = RoundedCornerShape(cornerRadius),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (!isTextFieldFocused) {
                // If text field is not in focus
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = scrollState
                ) {
                    items(
                        count = Constants.listOfCategories.size,
                    ) { index: Int ->
                        val color = Constants.colors[index.mod(Constants.colors.size)]
                        CategoryItem(
                            category = Constants.listOfCategories[index],
                            color = color,
                            onCategoryClick = {
                                searchScreenViewModel.categoryHeadline(Constants.listOfCategories[index].name)
                                navHostController.navigate(NavigationScreens.SEARCH_RESULT_SCREEN.name)
                            }
                        )
                    }
                }
            } else {
                // If text field if focused, show search history
                LazyColumn() {
                    items(recentSearch.value) { item: RecentSearchEntity ->
                        RecentSearchItem(
                            string = item.string,
                            onDeleteRecentSearch = {
                                searchScreenViewModel.deleteSearchTerm(it)
                            },
                            onRecentSearchClick = {
                                searchScreenViewModel.searchArticle(it)
                                navHostController.navigate(NavigationScreens.SEARCH_RESULT_SCREEN.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: NewsCategoryItem,
    color: Color,
    onCategoryClick: ()-> Unit
) {
    Card(
        backgroundColor = color,
        elevation = 4.dp,
        modifier = Modifier
            .width(70.dp)
            .height(100.dp)
            .padding(8.dp)
            .clickable {
                println("Category ${category.name} clicked")
                onCategoryClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = category.name,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily(Font(R.font.delagothic_one_regular)),
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun SearchScreenPreview() {
    NewsAppTheme {
        
    }
}