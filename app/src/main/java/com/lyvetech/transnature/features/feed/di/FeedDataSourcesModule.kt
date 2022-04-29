package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.features.feed.data.local.FeedLocalDataSource
import com.lyvetech.transnature.features.feed.data.local.FeedLocalDataSourceImpl
import com.lyvetech.transnature.features.feed.data.remote.FeedRemoteDataSource
import com.lyvetech.transnature.features.feed.data.remote.FeedRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FeedDataSourcesModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: FeedLocalDataSourceImpl): FeedLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: FeedRemoteDataSourceImpl): FeedRemoteDataSource
}