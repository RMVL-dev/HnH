package com.example.lesson23

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    override fun onAttach(context: Context) {
        Log.d("LifeCycle", "fragment first onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LifeCycle", "fragment first onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("LifeCycle", "fragment first onCreateView")
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onStart() {
        Log.d("LifeCycle", "fragment first onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("LifeCycle", "fragment first onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("LifeCycle", "fragment first onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("LifeCycle", "fragment first onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("LifeCycle", "fragment first onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("LifeCycle", "fragment first onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("LifeCycle", "fragment first onDetach")
        super.onDetach()
    }

}