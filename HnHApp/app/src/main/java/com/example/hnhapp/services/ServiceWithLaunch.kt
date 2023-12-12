package com.example.hnhapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServiceWithLaunch:Service(){
    private val TAG = "WITH_LAUNCH"
    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "ServiceWithLaunch onBind method")
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "ServiceWithLaunch onCreate method")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG, "ServiceWithLaunch onDestroy method")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "ServiceWithLaunch onStartCommand method")
        return START_STICKY
    }
}