package com.example.hnhapp.di

import android.content.Context
import com.example.hnhapp.HnHApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideApplicationContext(application:HnHApplication): Context =
        application.applicationContext
}