package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import com.lyvetech.transnature.features.feed.data.local.dao.FeedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTransNatureDao(
        transNatureDatabase: TransNatureDatabase
    ): FeedDao = transNatureDatabase.dao
}