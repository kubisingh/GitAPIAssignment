package com.omni.arch.data.cache

import android.graphics.Bitmap
import java.lang.ref.SoftReference
import java.util.concurrent.ConcurrentHashMap

class BitmapCacheProvider: CoreCache{
    private val cache: ConcurrentHashMap<String, SoftReference<Bitmap>> = ConcurrentHashMap()
    override fun add(key: String, value: Any) {
        cache.put(key, SoftReference(value as Bitmap))
    }

    override fun remove(key: String) {
        cache.remove(key)
    }

    override fun get(key: String): Bitmap? {
        if (isDataAvailable(key)) {
            return cache.get(key)?.get()
        }
        return null
    }

    override fun clear() {
        cache.clear()
    }

    override fun isDataAvailable(key: String): Boolean {
        cache.get(key)?.let { return true } ?: run { return false }
    }
}