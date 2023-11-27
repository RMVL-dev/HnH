package com.example.hnhapp.presentation.productItemFragment

import android.graphics.Color
import android.os.Bundle
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

    private var bindingList:MutableList<TextView> = emptyList<TextView>().toMutableList()

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
        initBindingList()
        setTextForAllSizes()
        initItem(position = 0)
        initItem(position = 1)
        initItem(position = 2)
        initItem(position = 3)

    }

    fun setOnclick(click:(Int)->Unit){
        onClick = click
    }

    /**
     * создание листа биндингов для удоства работы с ними
     */
    private fun initBindingList(){
        bindingList.add(binding.sizeFirst)
        bindingList.add(binding.sizeSecond)
        bindingList.add(binding.sizeThird)
        bindingList.add(binding.sizeFourth)
    }

    private fun setTextForAllSizes(){
        binding.sizeFirst.text = listSizes[0].value
        binding.sizeSecond.text = listSizes[1].value
        binding.sizeThird.text = listSizes[2].value
        binding.sizeFourth.text = listSizes[3].value
    }


    /**
     * в заивисимости от доступности размера(позиция в списке размеров)
     * установка цвета и слушателя нажатий
     */
    private fun initItem(position:Int){
        context?.resources?.let {
            if (listSizes[position].isAvailable) {
                bindingList[position].isClickable = listSizes[position].isAvailable
                bindingList[position].setOnClickListener {
                    onClick(position)
                }
            } else {
                bindingList[position].setTextColor(Color.GRAY)
                bindingList[position].isClickable = listSizes[position].isAvailable
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}