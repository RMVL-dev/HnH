package com.example.hnhapp.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.hnhapp.dataBase.testEntitys.MainEntity

@Dao
interface TestProductDao {

    //@Insert(entity = TestEntity::class, onConflict = OnConflictStrategy.REPLACE)
    //suspend fun addItem(testEntity: TestEntity)

    @Insert(entity = MainEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(testEntity: MainEntity)

}
