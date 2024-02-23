package com.example.newsapp.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.screens.home.HomeScreen
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.search.SearchScreen
import com.example.newsapp.ui.screens.search.SearchScreenViewModel
import com.example.newsapp.ui.screens.webview.WebViewScreen
import com.example.newsapp.ui.screens.webview.WebViewViewModel

enum class NavigationScreens{
    HOME,
    ARTICLE_VIEW,
    SEARCH
}
@Composable
fun Navigation(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    scrollUpState: State<Boolean>
) {
    val webViewViewModel: WebViewViewModel = viewModel(factory = ViewModelProvider.factory)
    val searchScreenViewModel: SearchScreenViewModel = viewModel(factory = ViewModelProvider.factory)


    val categoryPosition by animateFloatAsState(if (scrollUpState.value) -150f else 0f)
    val scrollState = rememberLazyListState()

    homeScreenViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.HOME.name
    ){
        composable(route = NavigationScreens.HOME.name){
            HomeScreen(
                navHostController = navController,
                webViewViewModel = webViewViewModel,
                homeScreenViewModel = homeScreenViewModel,
                scrollState = scrollState,
                categoryPosition = categoryPosition
            )
        }
        composable(route = NavigationScreens.ARTICLE_VIEW.name){
            WebViewScreen(
                navHostController = navController,
                webViewViewModel = webViewViewModel
            )
        }
        composable(route = NavigationScreens.SEARCH.name){
            SearchScreen(searchScreenViewModel = searchScreenViewModel)
        }
    }
}