package com.lyvetech.transnature.core.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lyvetech.transnature.features.feed.data.remote.retrofit.FeedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideTransNatureServiceApi(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(FeedApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp3(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }
}