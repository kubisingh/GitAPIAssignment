package com.omni.arch.data

import android.util.Log
import com.omni.arch.data.cache.CacheProvider
import com.omni.arch.domain.Repo
import com.omni.arch.domain.RepositoryContract
import io.reactivex.Single
import javax.inject.Inject

class TrendingReposRepository @Inject constructor(private val webservice: GithubWebservice,
                                                      private val schedulersProvider: ISchedulersProvider)
    : RepositoryContract.ITrendingReposRepository {

    override fun loadTrendingRepos(language: String, since:String, dataTask:DataTask): Single<List<Repo>> {
        val key = callIdentifier()+"|"+language+"|"+since
        when (dataTask.callType) {
            CallType.NETWORK -> {
                return apiCall(language, since, key, dataTask.cacheProvider)
            }
            CallType.CACHE -> {
                return cacheCall(key, dataTask.cacheProvider)
            }
            else -> {
                if(dataTask.cacheProvider.isDataAvailable(key)) {
                    return cacheCall(key, dataTask.cacheProvider)
                }else{
                    return apiCall(language, since, key, dataTask.cacheProvider)
                }
            }
        }
    }

    fun apiCall(language: String, since:String, key: String, cacheProvider: CacheProvider):
            Single<List<Repo>> {
        val call = webservice
                .loadTrendingRepos(language, since)
                .subscribeOn(schedulersProvider.io())
        call.subscribe({ data ->
            cacheProvider.add(key, data)
        }, { error ->
            Log.e(callIdentifier(), error.message)
        })
        return call
    }

    fun cacheCall(key: String, cacheProvider: CacheProvider): Single<List<Repo>> {
        val value = cacheProvider.get(key)
        if(value is List<*>) {
            return Single.just(value as List<Repo>)
        }
        return Single.just(mutableListOf())
    }

    override fun callIdentifier(): String {
        return "api.developer"
    }
}