package com.example.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.screens.auth.AuthViewModel
import com.example.newsapp.ui.screens.home.HomeScreen
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.screens.home.headlines.HeadlinesScreen
import com.example.newsapp.ui.screens.profile.ProfileScreen
import com.example.newsapp.ui.screens.search.SearchScreen
import com.example.newsapp.ui.screens.search.SearchScreenViewModel
import com.example.newsapp.ui.screens.search.newscategory.NewsCategoryScreen
import com.example.newsapp.ui.screens.webview.WebViewScreen
import com.example.newsapp.ui.screens.webview.WebViewViewModel

@Composable
fun Navigation(
    navController: NavHostController,
) {
    val homeScreenViewModel:HomeScreenViewModel = viewModel(factory = ViewModelProvider.factory)
    val searchScreenViewModel: SearchScreenViewModel = viewModel(factory = ViewModelProvider.factory)
    val webViewScreenViewModel: WebViewViewModel = viewModel(factory = ViewModelProvider.factory)
    val authViewModel: AuthViewModel = viewModel(factory = ViewModelProvider.factory)
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.HOME_SCREEN.name
    ){
        composable(route = NavigationScreens.HOME_SCREEN.name){
            HomeScreen(
                navHostController = navController,
                homeScreenViewModel = homeScreenViewModel,
                webViewViewModel = webViewScreenViewModel
            )
        }
        composable(route = NavigationScreens.ARTICLE_VIEW_SCREEN.name){
            WebViewScreen(
                navHostController = navController,
                webViewViewModel = webViewScreenViewModel
            )
        }
        composable(route = NavigationScreens.SEARCH_SCREEN.name){
            SearchScreen(
                navHostController = navController,
                searchScreenViewModel = searchScreenViewModel
            )
        }
        composable(route = NavigationScreens.PROFILE_SCREEN.name){
            ProfileScreen(
                authViewModel = authViewModel
            )
        }
        composable(route = NavigationScreens.HEADLINE_ARTICLES_SCREEN.name){
            HeadlinesScreen(
                navHostController = navController,
                homeScreenViewModel = homeScreenViewModel,
                webViewViewModel = webViewScreenViewModel
            )
        }
        composable(route = NavigationScreens.SEARCH_RESULT_SCREEN.name){
            NewsCategoryScreen(
                navHostController = navController,
                searchScreenViewModel = searchScreenViewModel,
                webViewViewModel = webViewScreenViewModel,
                homeScreenViewModel = homeScreenViewModel
            )
        }
    }
}