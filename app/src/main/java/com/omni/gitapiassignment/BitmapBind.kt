package com.omni.gitapiassignment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import android.os.Handler


fun bindBitmapToImage (view: ImageView, url: String){
    /*val target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            Log.d("TOOOOK","Loaded "+url)
            bitmap?.let {
                val bitmap = Bitmap.createScaledBitmap(it, 200, 200, false)
                view.setImageBitmap(bitmap)
                BaseApplication.instance.imageCacheProvider.add(url, bitmap)
            }
        }
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            Log.d("TOOOOK","Failed "+url)
        }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            Log.d("TOOOOK","Prepare "+url)
        }
    }*/
    if(BaseApplication.instance.imageCacheProvider.isDataAvailable(url)) {
        view.setImageBitmap(BaseApplication.instance.imageCacheProvider.get(url))
    } else {

        Picasso
                .get()
                .load(Uri.parse(url))
                .placeholder(R.drawable.ic_octoface)
                .error(R.drawable.ic_octoface)
                .into(weakTarget().load(view, url))
    }
}

class weakTarget: Target {
    lateinit var view: ImageView
    lateinit var url: String
    fun load(view: ImageView, url: String): weakTarget {
        this.url = url
        this.view = view
        return this
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
       Log.d("TOOOOK", "Prepare " + url)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        Log.d("TOOOOK", "Failed " + url)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        Log.d("TOOOOK", "Loaded " + url)
        bitmap?.let {
            val bitmap = Bitmap.createScaledBitmap(it, 200, 200, false)
            Handler().postDelayed(Runnable {
                view.setImageBitmap(bitmap)
                view.invalidate()
            }, 200)

            BaseApplication.instance.imageCacheProvider.add(url, bitmap)
        }
    }
}