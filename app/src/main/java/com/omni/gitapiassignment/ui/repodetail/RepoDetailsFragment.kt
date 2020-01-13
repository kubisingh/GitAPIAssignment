package com.omni.gitapiassignment.ui.repodetail

import android.os.Bundle
import android.view.View
import com.omni.arch.domain.Repo
import com.omni.gitapiassignment.KEY_DATA
import com.omni.gitapiassignment.R
import com.omni.gitapiassignment.bindBitmapToImage
import com.omni.gitapiassignment.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_repo_details.*

class RepoDetailsFragment : BaseFragment() {

    companion object {
        fun newInstance(repoBundle: Bundle?): RepoDetailsFragment {
            val fragment = RepoDetailsFragment()
            fragment.arguments = repoBundle
            return fragment
        }
    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Repo>(KEY_DATA)?.let { repo ->
            bindBitmapToImage(shared_image_repo_owner_detail, repo.avatar)
            shared_text_name_detail.text = repo.name
            shared_text_username_detail.text = repo.username
            text_repo_description.text = repo.repo.description
            text_type.text = repo.type
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_repo_details
}