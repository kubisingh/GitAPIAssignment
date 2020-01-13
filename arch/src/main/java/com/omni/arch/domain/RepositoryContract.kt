package com.omni.arch.domain

import com.omni.arch.data.DataTask
import io.reactivex.Single

interface RepositoryContract {
    interface ITrendingReposRepository {
        fun loadTrendingRepos(language: String, since:String, dataTask:DataTask): Single<List<Repo>>
        fun callIdentifier(): String
    }
}