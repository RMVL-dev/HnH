package com.example.hnhapp.data.productResponse

import com.google.gson.annotations.SerializedName

data class Size(
    @SerializedName("value")
    val value:String,
    @SerializedName("isAvailable")
    val isAvailable: Boolean
)
