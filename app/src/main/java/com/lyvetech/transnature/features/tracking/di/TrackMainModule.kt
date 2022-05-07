package com.lyvetech.transnature.features.tracking.di

import android.content.SharedPreferences
import com.lyvetech.transnature.core.util.Constants.KEY_NAME
import com.lyvetech.transnature.core.util.Constants.KEY_WEIGHT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackMainModule {

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 0f)
}