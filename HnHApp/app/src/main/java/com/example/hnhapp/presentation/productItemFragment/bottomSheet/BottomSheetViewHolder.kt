package com.example.hnhapp.presentation.productItemFragment.bottomSheet

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Size

class BottomSheetViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val sizeView = view.findViewById<TextView>(R.id.size_bottom_sheet)
    private var onClick:()->Unit = {}
    fun bind(
        size:Size
    ){
        sizeView.text = size.value
        if (size.isAvailable) {
            sizeView.isClickable = size.isAvailable
            sizeView.setOnClickListener {
                onClick()
            }
        } else {
            sizeView.setTextColor(Color.GRAY)
            sizeView.isClickable = size.isAvailable
        }
    }

    fun setOnClick(click:()->Unit ){
        onClick = click
    }
}