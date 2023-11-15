package com.example.hnhapp.di

import com.example.hnhapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun provideMainActivity():MainActivity
}