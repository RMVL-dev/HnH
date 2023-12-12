package com.example.hnhapp.presentation.accountSettings.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R

class BottomSheetRecyclerViewAdapter(
    private val list:List<String>
): RecyclerView.Adapter<BottomSheetViewHolder>() {

    private var onClick: (Int) -> Unit = {}
    private var ordinaryOnClick: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder =
        BottomSheetViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_view_holder, parent, false)
        )

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.setOnclick{
            onClick(position)
        }
        holder.bind(list[position])
    }

    fun setOtherFiledClick(
        onClick: (Int) -> Unit
    ){
        this.onClick = onClick
    }
}