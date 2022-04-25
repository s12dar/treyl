package com.lyvetech.transnature.core.di

import android.os.Bundle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Provides
    fun provideBundle(
    ): Bundle = Bundle()
}