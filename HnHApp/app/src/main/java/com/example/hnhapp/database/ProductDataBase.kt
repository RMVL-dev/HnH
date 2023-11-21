package com.example.hnhapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnhapp.data.productResponse.Badge
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.data.productResponse.Size
import com.example.hnhapp.database.ProductDataBase.Companion.DB_VERSION

@TypeConverters(
    Converters::class
)
@Database(
    entities = [
        ProductEntity::class
        //Product::class,
        //Badge::class,
        //Size::class
    ],
    version = DB_VERSION,
)
abstract class ProductDataBase: RoomDatabase() {

    companion object{
        const val DB_NAME = "products.db"
        const val DB_VERSION = 1
    }

    abstract fun createDAO():ProductDAO
}