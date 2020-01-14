package com.omni.gitapiassignment.ui.trendings.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.omni.gitapiassignment.R
import com.omni.gitapiassignment.ui.trendings.adapter.DataBindingViewHolder
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.support.v4.view.ViewCompat
import com.omni.gitapiassignment.ui.trendings.action.Action
import com.omni.gitapiassignment.ui.trendings.action.ActionManager
import com.omni.gitapiassignment.ui.trendings.action.ActionType
import com.omni.gitapiassignment.ui.trendings.view.fragments.RepoDetailsFragment
import com.omni.gitapiassignment.ui.trendings.view.fragments.TrendingReposFragment


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var actionManager: ActionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        actionManager.onActionListener = ::fireAction

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            actionManager.fire(Action(ActionType.ACTION_TRENDING_REPOS))
        }
    }

    private fun fireAction(action: Action) {
        when (action.type) {
            ActionType.UNKNOWN -> Log.w(javaClass.simpleName, "Unknown Action Fired!")
            ActionType.ACTION_TRENDING_REPOS -> transition(TrendingReposFragment.newInstance(), replace = false)
            ActionType.ACTION_REPO -> {
                val detailsFragment = RepoDetailsFragment.newInstance(action.data)
                transition(detailsFragment, replace = true, holder= action.holder)
            }
        }
    }

    private fun transition(fragment: BaseFragment, keepCurrent: Boolean = true,
                           replace: Boolean = true, holder: DataBindingViewHolder? = null) {
        if (!keepCurrent && supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }

        val transaction = supportFragmentManager
                .beginTransaction()
                holder?.let {
                    transaction.addSharedElement(it.imageView, ViewCompat.getTransitionName(it.imageView))
                            .addSharedElement(it.nameView, ViewCompat.getTransitionName(it.nameView))
                } ?: run {
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                }

        if (replace) {
            transaction
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragment.javaClass.simpleName)
        } else {
            transaction.add(R.id.container, fragment)
        }

        transaction.commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            fragmentDispatchingAndroidInjector

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
    }
}
