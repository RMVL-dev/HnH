package com.example.hnhapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServiceWithBound:Service() {

    companion object{
        const val TAG = "WITH_BOUND"
    }

    private val binder:IBinder = LocalBinder()

    override fun onCreate() {
        Log.d(TAG, "ServiceWithBound onCreate method")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "ServiceWithBound onStartCommand method")
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "ServiceWithBound onBind method")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "ServiceWithBound onUnbind method")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "ServiceWithBound onRebind method")
        super.onRebind(intent)
    }
    override fun onDestroy() {
        Log.d(TAG, "ServiceWithBound onDestroy method")
        super.onDestroy()
    }

}