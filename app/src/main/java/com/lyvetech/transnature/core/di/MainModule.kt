package com.lyvetech.transnature.core.di

import android.content.Context
import android.content.SharedPreferences
import com.lyvetech.transnature.core.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext app: Context
    ): SharedPreferences = app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
}