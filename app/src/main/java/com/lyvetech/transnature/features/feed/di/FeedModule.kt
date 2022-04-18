package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.features.feed.data.local.FeedDao
import com.lyvetech.transnature.features.feed.data.remote.FeedApiService
import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.data.repository.FeedRepositoryImpl
import com.lyvetech.transnature.features.feed.domain.usecase.GetAllTrailsUseCase
import com.lyvetech.transnature.features.feed.domain.usecase.GetSearchedTrailsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedModule {

    @Binds
    abstract fun provideFeedRepository(
        feedRepositoryImpl: FeedRepositoryImpl
    ): FeedRepository

    companion object {

        @Provides
        fun provideFeedApiService(
            retrofit: Retrofit
        ): FeedApiService = retrofit.create(FeedApiService::class.java)

        @Provides
        fun provideTransNatureDao(
            transNatureDatabase: TransNatureDatabase
        ): FeedDao = transNatureDatabase.dao

        @Provides
        fun provideAllTrailsUseCase(
            feedRepository: FeedRepository
        ): GetAllTrailsUseCase = GetAllTrailsUseCase(feedRepository)

        @Provides
        fun provideSearchedTrailsUseCase(
            feedRepository: FeedRepository
        ): GetSearchedTrailsUseCase = GetSearchedTrailsUseCase(feedRepository)
    }
}
