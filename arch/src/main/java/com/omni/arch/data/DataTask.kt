package com.omni.arch.data

import com.omni.arch.data.cache.CacheProvider

class DataTask (
        val callType: CallType,
        val cacheProvider: CacheProvider
)