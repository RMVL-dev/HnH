package com.example.hnhapp.di

import android.content.Context
import androidx.room.Room
import com.example.hnhapp.dataBase.SecondTestDataBase
import dagger.Module
import dagger.Provides

@Module
class TestStorageModule {

    @Provides
    fun createDataBase(context: Context) =
        Room.databaseBuilder(context, SecondTestDataBase::class.java, "DATABASE_NAME_2").build()

    @Provides
    fun createDAO(testDataBase: SecondTestDataBase) = testDataBase.createTestPlease()

}