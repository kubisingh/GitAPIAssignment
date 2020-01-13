package com.omni.gitapiassignment.ui.trendings.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.omni.arch.data.DataTask
import com.omni.arch.data.Error
import com.omni.arch.data.ISchedulersProvider
import com.omni.arch.data.CallType
import com.omni.arch.domain.Repo
import com.omni.arch.domain.RepositoryContract
import com.omni.gitapiassignment.BaseApplication
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class TrendingReposViewModel(
        private val repository: RepositoryContract.ITrendingReposRepository,
        private val schedulers: ISchedulersProvider
) : ViewModel() {
    val errorsLiveData: LiveData<Error> by lazy {
        MutableLiveData<Error>()
    }
    val reposLiveData: LiveData<MutableList<Repo>> by lazy {
        MutableLiveData<MutableList<Repo>>()
    }

    fun loadTrendingRepos(language: String, since:String) {
        val dataCall = DataTask(CallType.DATA, BaseApplication.instance.cacheProvider)
        repository
                .loadTrendingRepos(language, since, dataCall)
                .observeOn(schedulers.ui())
                .subscribe({
                    (errorsLiveData as MutableLiveData<*>).value = Error.SUCCESS
                    (reposLiveData as MutableLiveData<*>).value = it
                }, { error ->
                    (errorsLiveData as MutableLiveData<*>).value = when (error) {
                        is HttpException -> {
                            if (error.code() == 422) {
                                Error.NO_MORE_DATA
                            } else {
                                Error.UNKNOWN
                            }
                        }
                        is SocketTimeoutException -> Error.TIMEOUT
                        is IOException -> Error.DISCONNECTED
                        else -> Error.UNKNOWN
                    }
                })
    }
}