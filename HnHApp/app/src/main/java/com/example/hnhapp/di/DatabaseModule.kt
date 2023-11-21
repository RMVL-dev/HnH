package com.example.hnhapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.hnhapp.database.ProductDataBase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun createDatabase(context: Context) =
        Room.databaseBuilder(context, ProductDataBase::class.java, ProductDataBase.DB_NAME)
            //.addMigrations(Migration(4,7,{}))
            .build()

    @Provides
    fun getItemDao(dataBase: ProductDataBase) = dataBase.createDAO()

}