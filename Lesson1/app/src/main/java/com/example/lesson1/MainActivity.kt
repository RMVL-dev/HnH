package com.example.lesson1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            mainActivity = this@MainActivity
        }
    }

    /**method for navigation to second activity*/
    fun goToSecondActivity(){
        startActivity(SecondActivity.secondActivityIntent(this))
    }

    /**method for navigation to third activity*/
    fun goToThirdActivity(){
        startActivity(ThirdActivity.thirdActivityIntent(this))
    }

}