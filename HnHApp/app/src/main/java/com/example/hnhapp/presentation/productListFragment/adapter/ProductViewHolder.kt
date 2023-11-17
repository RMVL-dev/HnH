package com.example.hnhapp.presentation.productListFragment.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product
import com.google.android.material.button.MaterialButton

class ProductViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val image = view.findViewById<ImageView>(R.id.product_image)
    private val title = view.findViewById<TextView>(R.id.product_title)
    private val price = view.findViewById<TextView>(R.id.product_price)
    private val department = view.findViewById<TextView>(R.id.product_department)
    private val buy = view.findViewById<MaterialButton>(R.id.product_buy)
    fun bind(
        click: () -> Unit,
        product: Product
    ){
        Glide.with(image).load(product.previewImageUrl).into(image)
        title.text = product.title
        price.text = product.price
        department.text = product.department
        buy.setOnClickListener {
            click()
        }
    }

}