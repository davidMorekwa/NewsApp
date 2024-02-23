package com.example.newsapp.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.data.model.NewsCategoryItem
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel = viewModel(factory = ViewModelProvider.factory)
) {
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    var scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Red)
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
//                    searchScreenViewModel.searchArticle(query = searchValue)
                }
            ),
            shape = RoundedCornerShape(25.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Recent search")
        LazyVerticalGrid(columns = GridCells.Fixed(2)){
            items(
                count = Constants.listOfCategories.size,
            ){ index: Int ->
                val color = Constants.colors[index.mod(Constants.colors.size)]
                CategoryItem(
                    category = Constants.listOfCategories[index],
                    color = color,
                    onCategoryClick = {
                        searchScreenViewModel.categoryHeadline(Constants.listOfCategories[index].name)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(category: NewsCategoryItem, color: Color, onCategoryClick: (category: NewsCategoryItem)-> Unit) {
    Surface(
        shadowElevation = 4.dp,
        color = color,
        modifier = Modifier
            .width(70.dp)
            .height(100.dp)
            .padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(5.dp))
            .clickable { onCategoryClick(category) }
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


@Preview
@Composable
fun SearchScreenPreview() {
    NewsAppTheme {
        SearchScreen()
    }
}