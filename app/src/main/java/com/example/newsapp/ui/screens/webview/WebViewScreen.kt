package com.example.newsapp.ui.screens.webview

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.newsapp.ui.components.CircleShapeIndicator
import com.example.newsapp.ui.navigation.NavigationScreens
import com.example.newsapp.ui.screens.webview.WebView.WebViewImpl
import com.example.newsapp.ui.theme.NewsAppTheme

const val TAG = "WEBVIEW SCREEEN"
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebViewScreen(
    webViewViewModel: WebViewViewModel,
    navHostController: NavHostController
) {
    val state = webViewViewModel.uistate.collectAsState()
    val url = state.value.url
    Log.d(TAG, "URL: ${state.value.url}")
    var webViewClient = remember {
        WebViewClient()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { navHostController.popBackStack() },
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                modifier = Modifier.height(35.dp)
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 45.dp)
        ) {
            if(state.value.url.isNullOrBlank()){
                CircleShapeIndicator()
            }else {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            webViewClient = webViewClient
                            addJavascriptInterface(WebViewImpl(context), "Android")
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.setSupportZoom(true)
                            webChromeClient = WebChromeClient()
                            loadUrl(url)
                        }
                    },
//                    update = { webView ->
//                        webView.loadUrl(url)
//                    }

                )
                BackHandler {
                    // Handle back button press if needed
                    if (webViewClient.shouldOverrideUrlLoading(url)) {
                        // Custom handling for specific URLs if needed
                        navHostController.navigate(NavigationScreens.HOME_SCREEN.name)
                    }
                }
            }
        }
    }
}
fun WebViewClient.shouldOverrideUrlLoading(url: String): Boolean {
    // Implement custom logic to decide whether to override URL loading
    if (url.startsWith("http://") || url.startsWith("https://")) {
        return false // Load the URL in WebView
    }
    return true // Do not load the URL in WebView
}

@Preview
@Composable
fun WebViewScreenPreview() {
    NewsAppTheme {
//        WebViewScreen()
    }
}