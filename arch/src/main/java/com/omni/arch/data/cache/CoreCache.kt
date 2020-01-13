package com.omni.arch.data.cache

interface CoreCache {
    fun add(key: String, value: Any)
    fun remove(key: String)
    fun get(key: String): Any?
    fun clear()
    fun isDataAvailable(key: String): Boolean
}