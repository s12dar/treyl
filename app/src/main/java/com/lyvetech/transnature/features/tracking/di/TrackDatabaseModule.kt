package com.lyvetech.transnature.features.tracking.di

import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.features.tracking.data.local.dao.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TrackDatabaseModule {

    @Singleton
    @Provides
    fun provideTrackDao(
        transNatureDatabase: TransNatureDatabase
    ): TrackDao = transNatureDatabase.trackDao
}