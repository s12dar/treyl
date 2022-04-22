package com.lyvetech.transnature.features.feed_info.di

import android.os.Bundle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FeedInfoModule {

    @Provides
    fun provideBundle(
    ): Bundle = Bundle()
}