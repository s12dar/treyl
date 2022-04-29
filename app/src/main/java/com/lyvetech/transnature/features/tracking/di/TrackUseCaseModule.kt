package com.lyvetech.transnature.features.tracking.di

import com.lyvetech.transnature.features.tracking.domain.usecase.InsertSessionUseCase
import com.lyvetech.transnature.features.tracking.domain.usecase.InsertSessionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TrackUseCaseModule {

    @Binds
    abstract fun bindTrackUseCaseImpl(
        insertSessionUseCase: InsertSessionUseCaseImpl
    ): InsertSessionUseCase
}