package com.omni.gitapiassignment.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.omni.gitapiassignment.R
import com.omni.gitapiassignment.ui.repodetail.RepoDetailsFragment
import com.omni.gitapiassignment.ui.trendings.adapter.DataBindingViewHolder
import com.omni.gitapiassignment.ui.trendings.view.TrendingReposFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.os.Build
import android.support.transition.Fade
import com.omni.gitapiassignment.ui.transition.TransitionFragment


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
                transition(detailsFragment, holder= action.holder)
            }
        }
    }

    private fun transition(fragment: BaseFragment, keepCurrent: Boolean = true,
                           replace: Boolean = true, holder: DataBindingViewHolder? = null) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(TransitionFragment())
            fragment.setEnterTransition(Fade())
            fragment.setExitTransition(Fade())
            fragment.setSharedElementReturnTransition(TransitionFragment())
        }
        if (!keepCurrent && supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }

        val transaction = supportFragmentManager
                .beginTransaction()
                holder?.let {
                    transaction.setReorderingAllowed(true)
                            .addSharedElement(it.imageView, "sharedImage")
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
}
