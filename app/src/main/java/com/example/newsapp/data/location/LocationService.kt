package com.example.newsapp.data.location

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.newsapp.R
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "LOCATION SERVICE"
class LocationService: Service() {
    private val serviceScope = CoroutineScope(SupervisorJob()+Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private var currentLongitude: Double = 0.0
    private var currentLatitude: Double = 0.0

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        locationClient = LocationClientImpl(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        when(intent?.action){
            ACTION_START -> {
                Log.d(TAG, "ACTION_START")
                actionStart()
            }
            ACTION_STOP -> {
                Log.d(TAG, "ACTION_STOP")
                actionStop()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("NotificationPermission")
    private fun actionStart(){
        Log.d(TAG, "actionStart()")
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        locationClient.getLocation((1000 * 60 * 10).toLong())
            .catch { e -> Log.e(TAG, "Error: ${e}") }
            .onEach { location: Location ->
                val lat = location.latitude.toString()
                val lng = location.longitude.toString()
                if(lat.toDouble() != currentLatitude || lng.toDouble() != currentLongitude) {
                    Log.d(TAG, "New Location: ($lat, $lng)")
                    updateLocation(lng.toDouble(), lat.toDouble())
                    sendLocationToViewModel(lat.toDouble(), lng.toDouble())
                }
                Log.d(TAG, "Location: ($lat, $lng)")
                val updatedNotification = notification.setContentText(
                    "Location: ($lat, $lng)"
                )
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)
        startForeground(1, notification.build())
    }
    private fun actionStop(){
        stopForeground(true)
        stopSelf()
    }
    private fun sendLocationToViewModel(latitude: Double, longitude: Double) {
        val intent = Intent("LocationUpdate")
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
    private fun updateLocation(longitude: Double, latitude: Double) {
        Log.d(TAG, "updateLocation()")
        currentLongitude = longitude
        currentLatitude = latitude
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object{
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

}