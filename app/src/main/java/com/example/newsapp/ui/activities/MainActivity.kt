package com.example.newsapp.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.repositories.remote.RemoteRepositoryImpl
import com.example.newsapp.data.utils.Constants.STORED_THEME
import com.example.newsapp.ui.ViewModelProvider
import com.example.newsapp.ui.components.BottomAppBar
import com.example.newsapp.ui.components.TopAppBar
import com.example.newsapp.ui.navigation.Navigation
import com.example.newsapp.ui.screens.home.HomeScreen
import com.example.newsapp.ui.screens.home.HomeScreenViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import androidx.compose.material.TopAppBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val TAG = "MAIN ACTIVITY"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val theme: Flow<Boolean> = applicationContext.dataStore.data.map { value: Preferences ->
            value[STORED_THEME] ?: false
        }

        setContent {
            val isDarkTheme = theme.collectAsState(initial = false)
            Log.i(TAG, "Theme retrieved ${isDarkTheme.value}")
            NewsAppTheme(useDarkTheme = isDarkTheme.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//                    Navigation(navHostController = navController)
                    NewsApp(navHostController = navController)
                }
            }
        }
    }
}
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DarkTheme")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsApp(
    navHostController: NavHostController
) {
    val homeScreenViewModel:HomeScreenViewModel = viewModel(factory = ViewModelProvider.factory)
    val scrollUpState = homeScreenViewModel.scrollUp.collectAsState()
    val bottomAppBarPosition by animateFloatAsState(if (scrollUpState.value) 150f else 0f)
    val topAppBarPosition by animateFloatAsState(if (scrollUpState.value) -150f else 0f)

    var selectedIcon by rememberSaveable {
        mutableStateOf(0)
    }
    val title = when(selectedIcon){
        0 -> "Headlines"
        1 -> "Search"
        3 -> "Bookmarks"
        else -> ""
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                position = bottomAppBarPosition,
                navHostController = navHostController,
                selectedIcon = selectedIcon,
                onIconClick = {
                    selectedIcon = it
                }
            )
        }
    ) {
        Navigation(
            navController = navHostController,
            homeScreenViewModel = homeScreenViewModel,
            scrollUpState = scrollUpState
        )
    }
}