package com.example.hnhapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnhapp.dataBase.converter.TestEntityTypeConverter
import com.example.hnhapp.dataBase.testEntitys.MainEntity

@TypeConverters(
    TestEntityTypeConverter::class
)
@Database(
    entities = [
        //TestEntity::class
        MainEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class SecondTestDataBase: RoomDatabase() {

    abstract fun createTestPlease(): ProductDao



}