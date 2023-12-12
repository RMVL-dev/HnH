package com.example.hnhapp.data.order.response

import com.google.gson.annotations.SerializedName

data class OrderResponseProduct(
    @SerializedName("productId")
    val id:String,
    @SerializedName("preview")
    val imageUrl:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("size")
    val size:String,
    @SerializedName("quantity")
    val quantity:Int,
    @SerializedName("price")
    val price:Int
)
