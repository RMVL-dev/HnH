package com.example.hnhapp.data.order.request

import com.google.gson.annotations.SerializedName

data class OrderRequestProduct(
    @SerializedName("ProductId")
    val productId:String,
    @SerializedName("Size")
    val size:String,
    @SerializedName("Quantity")
    val quantity:Int
)
