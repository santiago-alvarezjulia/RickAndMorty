package com.saj.rickandmorty.di

import com.saj.rickandmorty.ui.imagemanager.GlideImageLoader
import com.saj.rickandmorty.ui.imagemanager.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class ShowCharactersFragmentModule {

    @Binds
    abstract fun bindImageLoader(
        glideImageLoader: GlideImageLoader
    ): ImageLoader
}