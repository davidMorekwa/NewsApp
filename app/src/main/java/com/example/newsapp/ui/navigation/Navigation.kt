package com.example.newsapp.ui.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.screens.headlines.HeadlinesScreen
import com.example.newsapp.ui.screens.home.HomeScreen
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.profile.ProfileScreen
import com.example.newsapp.ui.screens.profile.ProfileScreenViewModel
import com.example.newsapp.ui.screens.search.SearchScreen
import com.example.newsapp.ui.screens.search.SearchScreenViewModel
import com.example.newsapp.ui.screens.search.newscategory.NewsCategoryScreen
import com.example.newsapp.ui.screens.webview.WebViewScreen
import com.example.newsapp.ui.screens.webview.WebViewViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    scrollUpState: State<Boolean>,
) {
    // viewmodel instantiation
    val webViewViewModel: WebViewViewModel = viewModel(factory = ViewModelProvider.factory)
    val searchScreenViewModel: SearchScreenViewModel = viewModel(factory = ViewModelProvider.factory)
    val profileScreenViewModel: ProfileScreenViewModel = viewModel(factory = ViewModelProvider.factory)


    val categoryPosition by animateFloatAsState(if (scrollUpState.value) -150f else 0f)
    val scrollState = rememberLazyListState()

    homeScreenViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.HOME_SCREEN.name
    ){
        composable(route = NavigationScreens.HOME_SCREEN.name){
            HomeScreen(
                navHostController = navController,
                webViewViewModel = webViewViewModel,
                homeScreenViewModel = homeScreenViewModel,
                scrollState = scrollState,
                categoryPosition = categoryPosition
            )
        }
        composable(route = NavigationScreens.ARTICLE_VIEW_SCREEN.name){
            WebViewScreen(
                navHostController = navController,
                webViewViewModel = webViewViewModel
            )
        }
        composable(route = NavigationScreens.SEARCH_SCREEN.name){
            SearchScreen(
                searchScreenViewModel = searchScreenViewModel,
                webViewViewModel = webViewViewModel,
                navHostController = navController
            )
        }
        composable(route = NavigationScreens.PROFILE_SCREEN.name){
            ProfileScreen(
//                profileScreenViewModel = profileScreenViewModel
            )
        }
        composable(route = NavigationScreens.HEADLINE_ARTICLES_SCREEN.name){
            HeadlinesScreen(
                homeScreenViewModel = homeScreenViewModel,
                webViewViewModel = webViewViewModel,
                navHostController = navController
            )
        }
        composable(route = NavigationScreens.SEARCH_RESULT_SCREEN.name){
            NewsCategoryScreen(
                searchScreenViewModel = searchScreenViewModel,
                navHostController = navController,
                webViewViewModel = webViewViewModel
            )
        }
    }
}