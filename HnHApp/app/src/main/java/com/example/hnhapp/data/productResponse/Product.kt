package com.example.hnhapp.data.productResponse

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("badge")
    val badge: List<Badge>,
    @SerializedName("preview")
    val previewImageUrl: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("sizes")
    val sizes: List<Size>,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: List<String>,
)
