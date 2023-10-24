package com.example.lesson23

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LifeCycle", "Activity onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        Log.d("LifeCycle", "Activity onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("LifeCycle", "Activity onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("LifeCycle", "Activity onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("LifeCycle", "Activity onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("LifeCycle", "Activity onDestroy")
        super.onDestroy()
    }
}