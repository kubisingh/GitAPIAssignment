package com.omni.gitapiassignment.ui.inject

import com.omni.gitapiassignment.ui.trendings.action.ActionManager
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideActionManager(): ActionManager = ActionManager.instance
}