package com.omni.arch.data

import com.omni.arch.domain.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubWebservice {

    @GET("developers")
    fun loadTrendingRepos(@Query("language") language: String, @Query("since") since:String): Single<List<Repo>>
}