package com.example.hnhapp.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnhapp.data.productResponse.Product

@Entity(tableName = ProductDAO.DATABASE_NAME)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "products")
    val products: List<Product>
)
