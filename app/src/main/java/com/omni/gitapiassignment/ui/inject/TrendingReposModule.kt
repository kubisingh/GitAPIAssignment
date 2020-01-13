package com.omni.gitapiassignment.ui.inject

import com.omni.arch.data.ISchedulersProvider
import com.omni.arch.domain.RepositoryContract
import com.omni.gitapiassignment.ui.trendings.viewmodel.TrendingReposViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TrendingReposModule {

    @Provides
    fun provideTrendingReposViewModelFactory(repository: RepositoryContract.ITrendingReposRepository, schedulers: ISchedulersProvider) =
            TrendingReposViewModelFactory(repository, schedulers)
}