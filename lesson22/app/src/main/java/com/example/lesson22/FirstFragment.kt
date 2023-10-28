package com.example.lesson22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson22.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var binding:FragmentFirstBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}