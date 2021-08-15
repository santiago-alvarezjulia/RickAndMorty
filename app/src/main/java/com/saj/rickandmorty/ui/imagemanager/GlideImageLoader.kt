package com.saj.rickandmorty.ui.imagemanager

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import javax.inject.Inject

class GlideImageLoader @Inject constructor(): ImageLoader {
    override fun loadImage(context: Context, imageView: ImageView, imageUrl: String) {
        GlideApp.with(context)
            .load(imageUrl)
            .into(imageView)
    }

    override fun loadCircleImage(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        GlideApp.with(fragment)
            .load(imageUrl)
            .circleCrop()
            .into(imageView)
    }
}