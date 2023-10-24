package com.example.lesson2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesson2.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var binding:FragmentFirstBinding? = null
    private val args:FirstFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.btNext?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.textView?.text = args.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}