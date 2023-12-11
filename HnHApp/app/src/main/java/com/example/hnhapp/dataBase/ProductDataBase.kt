package com.example.hnhapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnhapp.dataBase.ProductDataBase.Companion.DATA_BASE_VERSION
import com.example.hnhapp.dataBase.converter.EntityTypeConverter
import com.example.hnhapp.dataBase.testEntitys.MainEntity

@TypeConverters(
    EntityTypeConverter::class
)
@Database(
    entities = [
        MainEntity::class
    ],
    version = DATA_BASE_VERSION,
    exportSchema = false
)
abstract class ProductDataBase: RoomDatabase() {

    companion object{
        const val DATA_BASE_VERSION = 2
        const val DATA_BASE_NAME = "database.db"
    }

    abstract fun createTestPlease(): ProductDao



}