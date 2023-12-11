package com.example.hnhapp.presentation.productItemFragment.bottomSheet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Size
import com.example.hnhapp.databinding.BottomSheetSizesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SizesBottomSheet(
    private var listSizes:List<Size>
): BottomSheetDialogFragment() {

    private var _binding:BottomSheetSizesBinding? = null
    private val binding get() = _binding!!

    private var onClick:(Int)->Unit ={}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSizesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.bottom_sheet_sizes,container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = BottomSheetRecyclerAdapter(listOfSizes = listSizes)
        adapter.setOnClick(onClick)
        binding.bsRecycler.adapter = adapter
    }

    fun setOnclick(click:(Int)->Unit){
        onClick = click
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}