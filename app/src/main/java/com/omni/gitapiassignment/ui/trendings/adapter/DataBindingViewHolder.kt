package com.omni.gitapiassignment.ui.trendings.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

class DataBindingViewHolder(private val binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root) {
    var imageView: ImageView? = null
    fun bindVariable(variableId: Int, obj: Any) {
        binding.setVariable(variableId, obj)
        binding.executePendingBindings()
    }
}
