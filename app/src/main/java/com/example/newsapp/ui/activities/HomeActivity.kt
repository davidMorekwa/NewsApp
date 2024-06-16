package com.example.newsapp.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.components.BottomAppBar
import com.example.newsapp.ui.navigation.Navigation
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.firebase.auth.FirebaseAuth

const val TAG = "MAIN ACTIVITY"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DarkTheme")
class HomeActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
//        val theme: Flow<Boolean> = applicationContext.dataStore.data.map { value: Preferences ->
//            value[STORED_THEME] ?: false
//        }
        if (currentUser == null){
            val authIntent = Intent(this, AuthActivity::class.java)
            startActivity(authIntent)
        } else {
            setContent {
//            val isDarkTheme = theme.collectAsState(initial = false)
//            Log.i(TAG, "Theme retrieved ${isDarkTheme.value}")
                NewsAppTheme {
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

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsApp(
    navHostController: NavHostController,
) {

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
//            scrollUpState = scrollUpState,
        )
    }
}