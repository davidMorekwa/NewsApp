package com.example.newsapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.auth.AuthViewModel
import com.example.newsapp.ui.screens.auth.LoginScreen
import com.example.newsapp.ui.screens.auth.RegistrationScreen
import com.example.newsapp.ui.theme.NewsAppTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AuthNavigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AuthNavigation(
    navController: NavHostController,
) {
    val authViewModel: AuthViewModel = viewModel(factory = ViewModelProvider.factory)
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.LOGIN_SCREEN.name
    ){
        composable(route = NavigationScreens.LOGIN_SCREEN.name){
            LoginScreen(
                navHostController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = NavigationScreens.REGISTRATION_SCREEN.name){
            RegistrationScreen(
                navHostController = navController,
                authViewModel = authViewModel
            )
        }
    }
}