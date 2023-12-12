package com.example.hnhapp.data.order.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("createdDelivery")
    val createdDelivery: String,
    @SerializedName("dateDelivery")
    val dateDelivery:String,
    @SerializedName("deliveryAddress")
    val address:String,
    @SerializedName("status")
    val status: String,
    @SerializedName("products")
    val products:List<OrderResponseProduct>
)