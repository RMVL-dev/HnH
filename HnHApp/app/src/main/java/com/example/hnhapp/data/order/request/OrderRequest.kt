package com.example.hnhapp.data.order.request

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("House")
    val house: String,
    @SerializedName("Apartment")
    val apartment:String,
    @SerializedName("DateDelivery")
    val dateDelivery:String,
    @SerializedName("Products")
    val products:List<OrderRequestProduct?>
)
