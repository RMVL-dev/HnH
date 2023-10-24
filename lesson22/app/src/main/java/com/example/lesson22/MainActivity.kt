package com.example.lesson22

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val fragmentManager = supportFragmentManager

        binding?.toFirstFragment?.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragment_host, FirstFragment())
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
        }
        binding?.toSecondFragment?.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragment_host, SecondFragment())
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
        }
        binding?.toThirdFragment?.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragment_host, ThirdFragment())
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}