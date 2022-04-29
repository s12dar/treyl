package com.lyvetech.transnature.features.tracking.di

import com.lyvetech.transnature.features.tracking.data.local.TrackLocalDataSource
import com.lyvetech.transnature.features.tracking.data.local.TrackLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class TrackDataSourceModule {

    @Binds
    abstract fun bindTrackLocalDataSource(localDataSourceImpl: TrackLocalDataSourceImpl):
            TrackLocalDataSource

}