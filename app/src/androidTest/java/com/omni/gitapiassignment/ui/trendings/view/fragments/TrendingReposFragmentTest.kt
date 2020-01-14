package com.omni.gitapiassignment.ui.trendings.view.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.runner.AndroidJUnit4
import com.omni.gitapiassignment.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingReposFragmentTest{

    @Test
    fun clicktest() {
        //locate and click on the login button
        onView(with(R.id.shared_image_repo_owner)).perform(click())

    }
}