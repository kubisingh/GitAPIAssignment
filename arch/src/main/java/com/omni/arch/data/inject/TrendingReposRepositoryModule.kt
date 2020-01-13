package com.omni.arch.data.inject

import com.omni.arch.data.TrendingReposRepository
import com.omni.arch.domain.RepositoryContract
import dagger.Module
import dagger.Provides

@Module
class TrendingReposRepositoryModule {
    @Provides
    fun provideTrendingReposRepository(repository: TrendingReposRepository):
            RepositoryContract.ITrendingReposRepository = repository
}