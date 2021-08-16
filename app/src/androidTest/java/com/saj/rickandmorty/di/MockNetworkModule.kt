package com.saj.rickandmorty.di

import com.saj.rickandmorty.network.RickAndMortyWebService
import com.saj.rickandmorty.network.callAdapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule {

    @Provides
    fun httpLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideRickAndMortyWebService(okHttpClient: OkHttpClient): RickAndMortyWebService = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .baseUrl("http://localhost:8080/")
        .client(okHttpClient)
        .build()
        .create(RickAndMortyWebService::class.java)
}