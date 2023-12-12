package com.example.hnhapp.presentation.accountSettings.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hnhapp.R
import com.example.hnhapp.databinding.BottomSheetSizesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    private val list: List<String>
) :BottomSheetDialogFragment() {

    private var _binding: BottomSheetSizesBinding? = null
    private val binding get() = _binding!!

    private var onClick: (Int) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSizesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.bottom_sheet_sizes, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = BottomSheetRecyclerViewAdapter(list = list)
        adapter.setOtherFiledClick {
            onClick(it)
        }
        binding.bsRecycler.adapter = adapter
    }

    fun setOnClick(onClick: (Int) -> Unit){
        this.onClick = onClick
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}