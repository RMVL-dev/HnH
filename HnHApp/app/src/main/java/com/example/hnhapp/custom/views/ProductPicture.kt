package com.example.hnhapp.custom.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.hnhapp.R
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.databinding.PicturesBinding
import com.example.hnhapp.presentation.productItemFragment.adapters.ProductItemPicturesAdapter

class ProductPicture @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var _binding:PicturesBinding?
    val binding get() = _binding!!

    private lateinit var product:Product

    init {
        _binding = PicturesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.pictures,this, true)
        )
    }

    fun setProduct(product: Product){
        this.product = product
        initAdapter()
    }

    private fun initAdapter(){
        val adapter = ProductItemPicturesAdapter()
        adapter.setListPictures(list = product.images)
        binding.carouselProductImages.adapter = adapter
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}