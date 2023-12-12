package com.example.hnhapp.di

import com.example.hnhapp.presentation.accountSettings.AccountSettingsFragment
import com.example.hnhapp.presentation.orderFragment.OrderFragment
import com.example.hnhapp.presentation.productItemFragment.ProductItemFragment
import com.example.hnhapp.presentation.productListFragment.ProductListFragment
import com.example.hnhapp.presentation.signinfragment.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun productFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun productItemFragment(): ProductItemFragment

    @ContributesAndroidInjector
    abstract fun orderFragment():OrderFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment():AccountSettingsFragment
}