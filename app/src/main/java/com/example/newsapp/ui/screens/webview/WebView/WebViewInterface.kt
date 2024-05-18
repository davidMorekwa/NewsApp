package com.example.newsapp.ui.screens.webview.WebView

import android.webkit.JavascriptInterface

interface WebViewInterface {
    @JavascriptInterface
    fun onButtonClick()
}