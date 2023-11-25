package com.example.hnhapp.presentation.productItemFragment.adapters

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hnhapp.R

class ProductItemViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val image = view.findViewById<ImageView>(R.id.item_product_picture)

    fun bind(
        imageUrl: String
    ){
        Glide.with(image)
            .load(imageUrl)
            .into(image)

    }




}