package com.omni.arch.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.omni.arch.data.cache.CacheProvider
import com.omni.arch.domain.Repo
import com.omni.arch.domain.RepositoryContract
import io.reactivex.Single
import java.io.InputStream
import java.lang.reflect.Type
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
            CallType.MOCK -> {
                return mockCall(dataTask.mockType, dataTask.fileinputStream)
            }
            else -> {
                return if(dataTask.cacheProvider?.isDataAvailable(key) == true) {
                    cacheCall(key, dataTask.cacheProvider)
                }else{
                    apiCall(language, since, key, dataTask.cacheProvider)
                }
            }
        }
    }

    private fun apiCall(language: String, since:String, key: String, cacheProvider: CacheProvider?):
            Single<List<Repo>> {
        val call = webservice
                .loadTrendingRepos(language, since)
                .subscribeOn(schedulersProvider.io())
        call.subscribe({ data ->
            cacheProvider?.add(key, data)
        }, { error ->
            Log.e(callIdentifier(), error.message)
        })
        return call
    }

    private fun cacheCall(key: String, cacheProvider: CacheProvider?): Single<List<Repo>> {
        val value = cacheProvider?.get(key)
        if(value is List<*>) {
            return Single.just(value as List<Repo>)
        }
        return Single.just(mutableListOf())
    }

    private fun mockCall(mockType: String, inputStream: InputStream?): Single<List<Repo>> {
        try {
            inputStream?.let { stream ->
                val json = stream?.bufferedReader().use { it?.readText() }
                val listType: Type = object : TypeToken<List<Repo>>() {}.type
                return Single.just(Gson().fromJson<List<Repo>>(json, listType))
            }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Single.error(Throwable("Invalid Data"))
    }

    override fun callIdentifier(): String {
        return "api.developer"
    }
}