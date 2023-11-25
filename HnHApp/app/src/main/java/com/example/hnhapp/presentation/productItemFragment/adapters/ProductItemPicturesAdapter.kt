package com.example.hnhapp.presentation.productItemFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R

class ProductItemPicturesAdapter():RecyclerView.Adapter<ProductItemViewHolder>(){

    private var listOfPictures = mutableListOf<String>()

    fun setListPictures(list:List<String>){
        listOfPictures = list.toMutableList()
        while (listOfPictures.size<3){
            listOfPictures.add("")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder =
        ProductItemViewHolder(
            view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_picture, parent, false)
        )

    override fun getItemCount(): Int =
        listOfPictures.size

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(listOfPictures[position])
    }
}