package com.example.hnhapp.presentation.productItemFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.hnhapp.R
import com.example.hnhapp.databinding.FragmentProductItemBinding
import dagger.android.support.AndroidSupportInjection

class ProductItemFragment : Fragment() {

    private var _binding:FragmentProductItemBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar(title = resources.getText(R.string.preview_title_product).toString())

    }


    private fun initToolBar(title:String){
        binding.productItemToolbar
            .setupWithNavController(
                findNavController()
            )
        binding.productItemToolbar.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}