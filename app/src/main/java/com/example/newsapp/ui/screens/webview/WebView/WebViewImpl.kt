package com.example.newsapp.ui.screens.webview.WebView

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.activity.ComponentActivity

const val TAG = "WEBVIEW IMPL"
class WebViewImpl(private val context: Context){
    @JavascriptInterface
    fun onButtonClick() {
        Log.i(TAG, "Button Clicked")
    }
}