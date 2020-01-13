package com.omni.arch.data.cache

import java.lang.ref.SoftReference
import java.util.concurrent.ConcurrentHashMap


class CacheProvider : CoreCache {

    private val cache: ConcurrentHashMap<String, SoftReference<Any>> = ConcurrentHashMap()
    override fun add(key: String, value: Any) {
            cache.put(key, SoftReference(value))
    }

    override fun remove(key: String) {
        cache.remove(key)
    }

    override fun get(key: String): Any? {
        if(isDataAvailable(key)) {
            return cache.get(key)?.get()
        }
        return null
    }

    override fun clear() {
        cache.clear()
    }

    override fun isDataAvailable(key: String): Boolean {
        cache.get(key)?.let { return true } ?: run{ return false }
    }

}