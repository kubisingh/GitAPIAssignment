package com.omni.gitapiassignment.data

import com.omni.arch.data.ISchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersProvider : ISchedulersProvider {
    override fun ui(): Scheduler =
            AndroidSchedulers.mainThread()

    override fun io(): Scheduler =
            Schedulers.io()

}