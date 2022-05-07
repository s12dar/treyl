package com.lyvetech.transnature.features.splash.di

import android.content.SharedPreferences
import com.lyvetech.transnature.core.util.Constants.KEY_FIRST_TIME_TOGGLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SplashMainModule {

    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}