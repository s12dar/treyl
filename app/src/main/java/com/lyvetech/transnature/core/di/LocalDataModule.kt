package com.lyvetech.transnature.core.di

import android.app.Application
import androidx.room.Room
import com.lyvetech.transnature.core.data.local.TransNatureDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideTransNatureDatabase(app: Application): TransNatureDatabase {
        return Room.databaseBuilder(
            app,
            TransNatureDatabase::class.java, "transnature_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}