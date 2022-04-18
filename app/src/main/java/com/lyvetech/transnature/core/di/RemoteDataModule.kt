package com.lyvetech.transnature.core.di

import com.lyvetech.transnature.features.feed.data.remote.FeedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideTransNatureServiceApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FeedApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}