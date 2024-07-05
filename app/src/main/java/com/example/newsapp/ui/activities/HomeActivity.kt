package com.example.newsapp.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.data.location.LocationService
import com.example.newsapp.data.utils.Constants
import com.example.newsapp.ui.components.BottomAppBar
import com.example.newsapp.ui.navigation.Navigation
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        val isOnBoardingComplete: Flow<Boolean> = applicationContext.onBoardingDataStore.data.map { value: Preferences ->
            value[Constants.IS_ONBOARDING_COMPLETE] ?: false
        }
        if (currentUser == null){
            val authIntent = Intent(this, AuthActivity::class.java)
            startActivity(authIntent)
            finish()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
                0)
            setContent {
                val stateIsOnBoardingComplete by isOnBoardingComplete.collectAsState(initial = true)
                Log.d(TAG, "IsOnBoardingComplete: $stateIsOnBoardingComplete")
                Intent(applicationContext, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    startService(this)
                }
                if (!stateIsOnBoardingComplete) {
                    LaunchedEffect(key1 = Unit) {
                        val intent = Intent(this@HomeActivity, AuthActivity::class.java).apply {
                            putExtra("reason", "onBoarding Incomplete")
                        }
                        startActivity(intent)
                        finish()
                    }
                } else {
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

    override fun onDestroy() {
        super.onDestroy()
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
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