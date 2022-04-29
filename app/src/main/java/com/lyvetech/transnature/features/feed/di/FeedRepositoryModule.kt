package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.data.repository.FeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FeedRepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: FeedRepositoryImpl): FeedRepository
}