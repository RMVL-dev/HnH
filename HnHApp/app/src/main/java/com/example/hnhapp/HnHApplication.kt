package com.example.hnhapp

import com.example.hnhapp.di.DaggerApplicationComponent
import com.yandex.mapkit.MapKitFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

private const val MAP_API_KEY = "37ac6cb9-71dc-4774-b9f5-3954dfb9665e" //не постоянная вещь

class HnHApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAP_API_KEY)
    }
}