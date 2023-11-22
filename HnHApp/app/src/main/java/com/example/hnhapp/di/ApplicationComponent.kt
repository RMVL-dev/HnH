package com.example.hnhapp.di

import com.example.hnhapp.HnHApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
        TestStorageModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<HnHApplication> {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: HnHApplication):ApplicationComponent
    }

}