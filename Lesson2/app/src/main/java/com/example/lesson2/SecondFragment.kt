package com.example.lesson2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lesson2.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var binding: FragmentSecondBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.btSave?.setOnClickListener {
            findNavController()
                .navigate(
                    SecondFragmentDirections.actionSecondFragmentToFirstFragment(
                        text = binding?.editText?.text.toString()
                    )
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}