package com.lyvetech.transnature.features.feed.di

import android.os.Bundle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FeedMainModule {

    @Provides
    fun provideBundle(
    ): Bundle = Bundle()
}