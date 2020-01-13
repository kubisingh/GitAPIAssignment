package com.omni.gitapiassignment.ui.trendings.adapter

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.omni.gitapiassignment.bindBitmapToImage

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, url: String) {
        bindBitmapToImage(view, url)
    }

}