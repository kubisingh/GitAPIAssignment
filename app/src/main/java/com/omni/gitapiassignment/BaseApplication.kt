package com.omni.gitapiassignment

import android.app.Activity
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.omni.arch.data.cache.BitmapCacheProvider
import com.omni.arch.data.cache.CacheProvider
import com.omni.gitapiassignment.inject.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class BaseApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var cacheProvider: CacheProvider
    @Inject
    lateinit var imageCacheProvider: BitmapCacheProvider

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Dagger injection
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> =
            dispatchingAndroidInjector

    override fun onTerminate() {
        super.onTerminate()
        cacheProvider.clear()
        imageCacheProvider.clear()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        cacheProvider.clear()
        cacheProvider.clear()
    }
}