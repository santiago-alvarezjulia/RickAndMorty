package com.saj.rickandmorty.ui.imagemanager

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment

interface ImageLoader {
    fun loadImage(context: Context, imageView: ImageView, imageUrl: String)
    fun loadCircleImage(fragment: Fragment, imageView: ImageView, imageUrl: String)
}