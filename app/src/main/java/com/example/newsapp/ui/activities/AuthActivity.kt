package com.example.newsapp.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
import com.example.newsapp.ui.screens.onBoarding.OnBoardingScreen
import com.example.newsapp.ui.screens.onBoarding.OnBoardingViewModel
import com.example.newsapp.ui.theme.NewsAppTheme


val Context.onBoardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "OnBoarding")
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val reason = remember{ intent.getStringExtra("reason") ?: "No reason" }
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AuthNavigation(
                        navController = navController,
                        reason = reason
                    )
                }
            }
        }
    }
}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    reason: String
) {
    val authViewModel: AuthViewModel = viewModel(factory = ViewModelProvider.factory)
    val onBoardingViewModel: OnBoardingViewModel = viewModel(factory = ViewModelProvider.factory)
    NavHost(
        navController = navController,
        startDestination = if(reason == "onBoarding Incomplete") NavigationScreens.ON_BOARDING_SCREEN.name else NavigationScreens.LOGIN_SCREEN.name
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
        composable(route = NavigationScreens.ON_BOARDING_SCREEN.name){
            OnBoardingScreen(
                onBoardingViewModel = onBoardingViewModel,
            )
        }

    }
}