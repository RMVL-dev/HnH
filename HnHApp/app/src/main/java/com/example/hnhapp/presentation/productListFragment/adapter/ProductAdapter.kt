package com.example.hnhapp.presentation.productListFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product

class ProductAdapter(
    private val productList:List<Product>
): RecyclerView.Adapter<ProductViewHolder>() {

    private var _onClick: () -> Unit = {}
    private var _onCardClick: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view = view)
    }

    override fun getItemCount(): Int =
        productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(
            buttonClick = { _onClick() },
            cardClick = { _onCardClick() },
            product = productList[position]
        )
    }

    fun setOnClick(click:()->Unit){
        _onClick = click
    }

    fun onCardClick(click: () -> Unit){
        _onCardClick = click
    }

}