package com.example.hnhapp.dataBase.testEntitys

import com.example.hnhapp.data.productResponse.Badge
import com.example.hnhapp.data.productResponse.Size


data class ProductEntity(
    val id: String,
    val title: String,
    val department: String,
    val price: String,
    val badge: List<BadgeEntity>,
    val previewImageUrl: String,
    val images: List<String>,
    val sizes: List<SizeEntity>,
    val description: String,
    val details: List<String>,
)
