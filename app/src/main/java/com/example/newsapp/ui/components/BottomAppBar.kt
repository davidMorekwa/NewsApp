package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun BottomAppBar(
    position: Float,
    navHostController: NavHostController,
    selectedIcon: Int,
    onIconClick: (id: Int) -> Unit
) {

    var homeIcon by remember {
        mutableStateOf(Icons.Outlined.Home)
    }
    var searchIcon by remember {
        mutableStateOf(Icons.Outlined.Search)
    }
    var userIcon by remember {
        mutableStateOf(Icons.Outlined.Person)
    }
    var bookmarkIcon by remember {
        mutableStateOf(R.drawable.icons8_outlined_bookmark_50___)
    }

    when(selectedIcon){
        0 -> {
            homeIcon = Icons.Filled.Home
            searchIcon = Icons.Outlined.Search
            userIcon = Icons.Outlined.Person
            bookmarkIcon = R.drawable.icons8_outlined_bookmark_50___
        }
        1 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Filled.Search
            userIcon = Icons.Outlined.Person
            bookmarkIcon = R.drawable.icons8_outlined_bookmark_50___
        }
        2 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Outlined.Search
            userIcon = Icons.Outlined.Person
            bookmarkIcon = R.drawable.icons8_bookmark_50___
        }
        3 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Outlined.Search
            userIcon = Icons.Filled.Person
            bookmarkIcon = R.drawable.icons8_outlined_bookmark_50___
        }
    }

    Surface(
        modifier = Modifier
            .graphicsLayer { translationY = position }
            .height(35.dp),
        color = Color.Black
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    onIconClick(0)
                    navHostController.navigate(NavigationScreens.HOME_SCREEN.name)
                }
            ) {
                Icon(
                    imageVector = homeIcon,
                    contentDescription = "Home icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(25.dp)
                )
            }

            IconButton(
                onClick = {
                    onIconClick(1)
                    navHostController.navigate(NavigationScreens.SEARCH_SCREEN.name)
                }
            ) {
                Icon(
                    imageVector = searchIcon,
                    contentDescription = "Search icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(25.dp)
                )
            }
//            IconButton(onClick = { onIconClick(2) }) {
//                Icon(
//                    painter = painterResource(id = bookmarkIcon),
//                    contentDescription = "Bookmarks",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(25.dp)
//                )
//            }
            IconButton(
                onClick = {
                    onIconClick(3)
                    navHostController.navigate(NavigationScreens.PROFILE_SCREEN.name)
                }
            ) {
                Icon(
                    imageVector = userIcon,
                    contentDescription = "User Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomAppBarPreview() {
    NewsAppTheme {
        BottomAppBar(
            position = 1f,
            onIconClick = {},
            navHostController = rememberNavController(),
            selectedIcon = 1
        )
    }
}