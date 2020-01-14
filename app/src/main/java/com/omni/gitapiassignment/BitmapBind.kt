package com.omni.gitapiassignment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


fun bindBitmapToImage (view: ImageView, url: String){
    val target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            bitmap?.let {
                val bitmap = Bitmap.createScaledBitmap(it, 200, 200, false)
                view.setImageBitmap(bitmap)
                BaseApplication.instance.imageCacheProvider.add(url, bitmap)
            }
        }
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    }
    if(BaseApplication.instance.imageCacheProvider.isDataAvailable(url)) {
        view.setImageBitmap(BaseApplication.instance.imageCacheProvider.get(url))
    } else {

        Picasso
                .get()
                .load(Uri.parse(url))
                .placeholder(R.drawable.ic_octoface)
                .noFade()
                .error(R.drawable.ic_octoface)
                .into(target)
        view.setTag(target)
    }
}
