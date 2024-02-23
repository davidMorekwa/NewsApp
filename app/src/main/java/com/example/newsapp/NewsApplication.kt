package com.example.newsapp

import android.app.Application
import android.util.Log
import com.example.newsapp.data.di.AppContainer
import com.example.newsapp.data.di.AppContainerImpl

class NewsApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(applicationContext)
        Log.d("NEWS APPLICATION", "Instantiating the Application class")
    }
}