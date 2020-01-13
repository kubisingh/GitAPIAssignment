package com.omni.gitapiassignment.ui.trendings.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.omni.arch.domain.Repo
import com.omni.gitapiassignment.BR
import com.omni.gitapiassignment.R

class ReposRecyclerAdapter(val repos: MutableList<Repo>)
    : RecyclerView.Adapter<DataBindingViewHolder>() {
    var onRepoItemClickListener: ((Repo, DataBindingViewHolder) -> Unit)? = null
    var filterRepos: MutableList<Repo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DataBindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_repo, parent, false)
        val holder = DataBindingViewHolder(binding)
        holder.imageView = binding.root.findViewById<ImageView>(R.id.shared_image_repo_owner)
        return holder
    }

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bindVariable(BR.repo, repos[position])
        holder.itemView.setOnClickListener { _ ->
            onRepoItemClickListener?.let { it(repos[holder.adapterPosition], holder) }
        }
    }

    fun setFilter(input: String) {
        if(input.length > 1){
            if(filterRepos==null){
                filterRepos = mutableListOf()
                filterRepos?.addAll(repos)
            }
            repos.clear()
            filterRepos?.forEach {
                if(it.getSearchKeys().contains(input.toLowerCase())) {
                    repos.add(it)
                }
            }
            notifyDataSetChanged()
        } else {
            resetFilter()
        }
    }

    fun resetFilter() {
        filterRepos?.let {
            repos.clear()
            repos.addAll(it)
            filterRepos?.clear()
            filterRepos = null
            notifyDataSetChanged()
        }
    }

}