package com.example.lesson5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myCustomView.setData(
            data = listOf(
                5,
                15,
                25,
                35,
                45,
                55,
                65,
                75,
                100
            )
        )

    }
}