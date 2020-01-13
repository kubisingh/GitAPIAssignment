package com.omni.arch.data.inject

import com.omni.arch.data.cache.BitmapCacheProvider
import com.omni.arch.data.cache.CacheProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule{

    @Provides
    @Singleton
    fun provideCacheInterface(): CacheProvider {
        return CacheProvider()
    }

    @Provides
    @Singleton
    fun provideImageCacheInterface(): BitmapCacheProvider {
        return BitmapCacheProvider()
    }

}