package com.example.hnhapp.di

import android.content.Context
import androidx.room.Room
import com.example.hnhapp.dataBase.ProductDataBase
import com.example.hnhapp.dataBase.ProductDataBase.Companion.DATA_BASE_NAME
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun createDataBase(context: Context) =
        Room.databaseBuilder(context, ProductDataBase::class.java, DATA_BASE_NAME).build()

    @Provides
    fun createDAO(testDataBase: ProductDataBase) = testDataBase.createTestPlease()

}