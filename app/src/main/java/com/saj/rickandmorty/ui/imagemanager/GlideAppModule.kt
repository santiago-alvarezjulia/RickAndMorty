package com.saj.rickandmorty.ui.imagemanager

import android.annotation.SuppressLint
import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideAppModule : AppGlideModule() {
    @SuppressLint("CheckResult")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    }
}