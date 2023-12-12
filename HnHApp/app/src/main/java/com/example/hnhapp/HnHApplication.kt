package com.example.hnhapp

import com.example.hnhapp.di.DaggerApplicationComponent
import com.yandex.mapkit.MapKitFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class HnHApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY)
    }
}