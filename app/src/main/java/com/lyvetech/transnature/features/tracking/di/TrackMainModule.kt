package com.lyvetech.transnature.features.tracking.di

import android.content.Context
import android.content.SharedPreferences
import com.lyvetech.transnature.core.util.Constants.KEY_FIRST_TIME_TOGGLE
import com.lyvetech.transnature.core.util.Constants.KEY_NAME
import com.lyvetech.transnature.core.util.Constants.KEY_WEIGHT
import com.lyvetech.transnature.core.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackMainModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext app: Context
    ): SharedPreferences = app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 0f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}