package com.example.lesson22

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val locale = Resources.getSystem().configuration.locales[0]
        if ("ru" in locale.toLanguageTag()) {
            binding?.tvPoem?.text = getText(R.string.poem)
        }else{
            binding?.tvPoem?.text = getText(R.string.fail)
        }
        val size = requireActivity().windowManager.currentWindowMetrics.bounds.width()
        if (size < 325){
            binding?.tvPoem?.setTextColor(Color.RED)
        }
        when(Resources.getSystem().configuration.orientation){

            Configuration.ORIENTATION_LANDSCAPE -> {
                binding?.image?.setImageResource(R.drawable.pushkin_signature)
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                binding?.image?.setImageResource(R.drawable.saint_andrew_s_cross)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}