package com.example.hnhapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServiceWithBoth: Service() {
    private val binder = LocalBinder()

    companion object{
        const val TAG = "BOTH_Service"
    }
    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "Service BOTH onBind method")
        return binder
    }

    override fun onCreate() {
        Log.d(TAG, "Service BOTH onCreate method")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service BOTH onStartCommand method")
        return START_NOT_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Service BOTH onUnbind method")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "Service BOTH onRebind method")
        super.onRebind(intent)
    }
    override fun onDestroy() {
        Log.d(TAG, "Service BOTH onDestroy method")
        super.onDestroy()
    }

}