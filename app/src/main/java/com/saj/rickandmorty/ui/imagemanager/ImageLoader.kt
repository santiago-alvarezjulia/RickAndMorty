package com.saj.rickandmorty.ui.imagemanager

import android.content.Context
import android.widget.ImageView

interface ImageLoader {
    fun loadImage(context: Context, imageView: ImageView, imageUrl: String)
}