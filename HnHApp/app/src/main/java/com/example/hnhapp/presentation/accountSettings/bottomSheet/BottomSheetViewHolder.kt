package com.example.hnhapp.presentation.accountSettings.bottomSheet

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R

class BottomSheetViewHolder(
    view:View
): RecyclerView.ViewHolder(view) {

    private val textView = view.findViewById<TextView>(R.id.size_bottom_sheet)
    private var onClick:() -> Unit = {}

    fun bind(
        occupation:String
    ){
        textView.text = occupation
        textView.setOnClickListener{
            onClick()
        }
    }

    fun setOnclick(onClick:()->Unit){
        this.onClick = onClick
    }
}