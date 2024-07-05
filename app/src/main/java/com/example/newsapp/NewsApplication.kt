package com.example.newsapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.newsapp.data.di.AppContainer
import com.example.newsapp.data.di.AppContainerImpl

class NewsApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(applicationContext)
        Log.d("NEWS APPLICATION", "Instantiating the Application class")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}