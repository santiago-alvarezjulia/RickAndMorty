package com.saj.rickandmorty.ui.imagemanager

import android.content.Context
import android.widget.ImageView
import javax.inject.Inject

class GlideImageLoader @Inject constructor(): ImageLoader {
    override fun loadImage(context: Context, imageView: ImageView, imageUrl: String) {
        GlideApp.with(context)
            .load(imageUrl)
            .into(imageView)
    }
}