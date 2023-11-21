package com.example.hnhapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hnhapp.data.productResponse.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDAO {

    companion object{
        const val DATABASE_NAME = "product_db"
        //const val DATA_BADGE_NAME = "badge_db"
        //const val DATA_SIZE_NAME = "size_db"
    }

    //@Query("SELECT * FROM $DATABASE_NAME")
    //fun getAllProducts(): Flow<List<Product>>

    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(productEntity:List<Product>)
}