package com.omni.gitapiassignment.inject

import com.omni.arch.data.inject.ApiServiceModule
import com.omni.arch.data.inject.CacheModule
import com.omni.gitapiassignment.BaseApplication
import com.omni.gitapiassignment.data.ApiModule
import com.omni.gitapiassignment.ui.inject.ApplicationModule
import com.omni.gitapiassignment.ui.inject.ContributorsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApiServiceModule::class,
    ApiModule::class,
    ApplicationModule::class,
    ContributorsModule::class,
    CacheModule::class
])
interface ApplicationComponent : AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApplication): Builder

        fun build(): ApplicationComponent
    }

}