package com.example.hnhapp.data.productResponse

import com.google.gson.annotations.SerializedName

data class Badge(
    @SerializedName("value")
    val value: String,
    @SerializedName("color")
    val color: String
)
