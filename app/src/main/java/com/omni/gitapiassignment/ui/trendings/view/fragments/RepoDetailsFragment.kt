package com.omni.gitapiassignment.ui.trendings.view.fragments

import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.view.View
import com.omni.arch.domain.Repo
import com.omni.gitapiassignment.KEY_DATA
import com.omni.gitapiassignment.R
import com.omni.gitapiassignment.bindBitmapToImage
import com.omni.gitapiassignment.ui.trendings.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_repo_details.*
import android.content.Intent
import android.net.Uri


class RepoDetailsFragment : BaseFragment() {

    companion object {
        fun newInstance(repoBundle: Bundle?): RepoDetailsFragment {
            val fragment = RepoDetailsFragment()
            fragment.arguments = repoBundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Repo>(KEY_DATA)?.let { repo ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                shared_image_repo_owner_detail.setTransitionName(repo.username)
                shared_text_name_detail.setTransitionName(repo.username+"_name")
            }
            bindBitmapToImage(shared_image_repo_owner_detail, repo.avatar)
            shared_text_name_detail.text = repo.name
            shared_text_username_detail.text = repo.username
            text_account_url.text = repo.repoUrl
            text_repo_name.text = repo.repo.name
            text_repo_description.text = repo.repo.description
            text_repo_url.text = repo.repo.url
            startPostponedEnterTransition()
            text_repo_url.setOnClickListener {
                openWebPage(text_repo_url.text?.toString())
            }
            text_account_url.setOnClickListener {
                openWebPage(text_account_url.text?.toString())
            }
        }
    }

    fun openWebPage(url: String?) {
        url?.let {
            val webpage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(getMainActivity()?.getPackageManager()) != null) {
                startActivity(intent)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_repo_details
}