package com.omni.gitapiassignment.ui.inject

import com.omni.arch.data.inject.TrendingReposRepositoryModule
import com.omni.gitapiassignment.ui.MainActivity
import com.omni.gitapiassignment.ui.trendings.view.TrendingReposFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributorsModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [TrendingReposRepositoryModule::class, TrendingReposModule::class])
    abstract fun bindTrendingReposFragment(): TrendingReposFragment
}