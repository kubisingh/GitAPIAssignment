package com.omni.arch.data

import com.omni.arch.data.cache.CacheProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.InputStream

class DataTask (
        val callType: CallType,
        val cacheProvider: CacheProvider?
){
     var mockType: String = "dataType1"
         get() = field
         set(value) {field = value}

    var fileinputStream: InputStream? = null
        get() = field
        set(value) {field = value}

    var scheduler: Scheduler? = null
        get() = field
        set(value) {field = value}

    fun getMockFilePath(identifier: String) = "mockdata/$mockType/$identifier.json"
}
