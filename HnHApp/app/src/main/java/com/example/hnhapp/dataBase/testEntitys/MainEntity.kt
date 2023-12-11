package com.example.hnhapp.dataBase.testEntitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.dataBase.ProductDao

@Entity(tableName = ProductDao.PRODUCT_ENTITY_NAME)
data class MainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val product: Product
)
