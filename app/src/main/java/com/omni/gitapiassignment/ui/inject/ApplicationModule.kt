package com.omni.gitapiassignment.ui.inject

import com.omni.gitapiassignment.ui.ActionManager
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideActionManager(): ActionManager = ActionManager.instance
}