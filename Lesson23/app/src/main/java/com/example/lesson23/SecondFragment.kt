package com.example.lesson23

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LifeCycle", "fragment second onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LifeCycle", "fragment second onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("LifeCycle", "fragment second onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onStart() {
        Log.d("LifeCycle", "fragment second onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("LifeCycle", "fragment second onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("LifeCycle", "fragment second onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("LifeCycle", "fragment second onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("LifeCycle", "fragment second onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("LifeCycle", "fragment second onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("LifeCycle", "fragment second onDetach")
        super.onDetach()
    }
}