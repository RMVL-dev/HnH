package com.example.hnhapp.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hnhapp.data.productResponse.Product
import com.example.hnhapp.dataBase.testEntitys.MainEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    companion object{
        const val PRODUCT_ENTITY_NAME = "product_entity"
    }
    @Insert(entity = MainEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(testEntity: MainEntity)

    @Query("select * from $PRODUCT_ENTITY_NAME")
    fun getProducts(): Flow<List<MainEntity>>

}
