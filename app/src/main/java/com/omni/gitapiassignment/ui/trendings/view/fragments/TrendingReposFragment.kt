package com.omni.gitapiassignment.ui.trendings.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.omni.arch.coreUI.disappear
import com.omni.arch.coreUI.show
import com.omni.arch.data.CallType
import com.omni.arch.data.DataTask
import com.omni.arch.data.Error
import com.omni.arch.domain.Repo
import com.omni.gitapiassignment.BaseApplication
import com.omni.gitapiassignment.KEY_DATA
import com.omni.gitapiassignment.R
import com.omni.gitapiassignment.ui.trendings.action.Action
import com.omni.gitapiassignment.ui.trendings.action.ActionManager
import com.omni.gitapiassignment.ui.trendings.action.ActionType
import com.omni.gitapiassignment.ui.trendings.adapter.DataBindingViewHolder
import com.omni.gitapiassignment.ui.trendings.adapter.ReposRecyclerAdapter
import com.omni.gitapiassignment.ui.trendings.view.BaseFragment
import com.omni.gitapiassignment.ui.trendings.viewmodel.TrendingReposViewModel
import com.omni.gitapiassignment.ui.trendings.viewmodel.TrendingReposViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_trending_repos.*
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    companion object {
        fun newInstance(): TrendingReposFragment = TrendingReposFragment()
    }

    @Inject
    lateinit var viewModelFactory: TrendingReposViewModelFactory
    @Inject
    lateinit var actionManager: ActionManager

    private val trendingReposVM: TrendingReposViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TrendingReposViewModel::class.java]
    }

    private var reposAdapter: ReposRecyclerAdapter? = null
    private var isFirstCall = true

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trendingReposVM.reposLiveData.observe(this, Observer<MutableList<Repo>>(::onChanged))
        trendingReposVM.errorsLiveData.observe(this, Observer<Error>(::onChanged))
    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        initUI()
        if (reposAdapter == null) {
            reposAdapter = ReposRecyclerAdapter(mutableListOf())
            reposAdapter?.onRepoItemClickListener = ::onRepoClicked
        }
        recycler_repos.adapter = reposAdapter
        recycler_repos.layoutManager = LinearLayoutManager(context)
        initActionEvent()
        swipe_repos.setOnRefreshListener {
            loadRepos()
        }
    }

    private fun initUI() {
        search_btn.setOnClickListener {
            if(text_search.visibility==View.VISIBLE){
                text_search.setText("")
                text_search.disappear()
                layout_selectors.show()
                switchButtonIcon(false)
                reposAdapter?.resetFilter()
            }else{
                text_search.setText("")
                text_search.show()
                layout_selectors.disappear()
                switchButtonIcon(true)
            }
        }
    }

    private fun loadRepos() {
        if (!isFirstCall) {
            swipe_repos.isRefreshing = true
            hideSearchUI()
            val dataCall = DataTask(CallType.DATA, BaseApplication.instance.cacheProvider)
            dataCall.scheduler = trendingReposVM.schedulers.ui()
            trendingReposVM.loadTrendingRepos(
                    selector_lang.selectedItem.toString(),
                    selector_since.selectedItem.toString(),
                    dataCall
            )
        }
        isFirstCall = false
    }

    private fun hideSearchUI(){
        if(text_search.visibility==View.VISIBLE) {
            text_search.setText("")
            text_search.disappear()
            layout_selectors.show()
            switchButtonIcon(false)
        }
    }

    private fun switchButtonIcon(isActive: Boolean){
        @DrawableRes
        var res : Int = android.R.drawable.ic_menu_search
        if(isActive) {
            res = android.R.drawable.ic_menu_close_clear_cancel
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            search_btn.setImageDrawable(getResources().getDrawable(
                    res,
                    activity?.application?.getTheme())
            )
        } else {
            search_btn.setImageDrawable(getResources().getDrawable(res))
        }

    }

    private fun onChanged(data: Any?) {
        swipe_repos.isRefreshing = false
        when (data) {
            is Error -> {
            }
            is MutableList<*> -> {
                reposAdapter?.repos?.clear()
                notifyAdapter(data as List<Repo>)
            }
        }
    }

    private fun notifyAdapter(data: List<Repo>) {
        reposAdapter?.repos?.addAll(data)
        recycler_repos.adapter.notifyDataSetChanged()
    }

    private fun onRepoClicked(repo: Repo, holder: DataBindingViewHolder) {
        val data = Bundle()
        data.putParcelable(KEY_DATA, repo)
        actionManager.fire(Action(ActionType.ACTION_REPO, data, holder))
    }

    private fun initActionEvent(){
        val listener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loadRepos()
            }
        }
        selector_lang.onItemSelectedListener = listener
        selector_since.onItemSelectedListener = listener
        text_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable) {
                reposAdapter?.setFilter(s.toString())
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_trending_repos
}