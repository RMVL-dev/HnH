package com.example.hnhapp.presentation.productItemFragment.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Size

class BottomSheetRecyclerAdapter(
    private val listOfSizes:List<Size>
):RecyclerView.Adapter<BottomSheetViewHolder>() {

    private var onClick:(Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_view_holder, parent, false)
        return BottomSheetViewHolder(view = view)
    }

    override fun getItemCount(): Int =
        listOfSizes.size

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(listOfSizes[position])
        holder.setOnClick {
            onClick(position)
        }
    }

    fun setOnClick(onClick:(Int) -> Unit){
        this.onClick = onClick
    }
}