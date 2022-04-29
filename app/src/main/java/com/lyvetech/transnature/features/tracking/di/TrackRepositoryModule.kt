package com.lyvetech.transnature.features.tracking.di

import com.lyvetech.transnature.features.tracking.data.repository.TrackRepository
import com.lyvetech.transnature.features.tracking.data.repository.TrackRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class TrackRepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: TrackRepositoryImpl): TrackRepository
}