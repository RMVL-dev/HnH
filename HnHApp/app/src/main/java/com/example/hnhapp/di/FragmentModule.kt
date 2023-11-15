package com.example.hnhapp.di

import com.example.hnhapp.signinfragment.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment
}