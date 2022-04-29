package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.features.feed.data.remote.retrofit.FeedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FeedNetworkModule {

    @Provides
    @Singleton
    fun provideFeedApiService(
        retrofit: Retrofit
    ): FeedApiService = retrofit.create(FeedApiService::class.java)
}