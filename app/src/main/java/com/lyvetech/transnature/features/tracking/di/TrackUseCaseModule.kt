package com.lyvetech.transnature.features.tracking.di

import com.lyvetech.transnature.features.tracking.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TrackUseCaseModule {

    @Binds
    abstract fun bindInsertSessionCase(
        insertSessionUseCase: InsertSessionUseCaseImpl
    ): InsertSessionUseCase

    @Binds
    abstract fun bindGetSessionsCase(
        getSessionsUseCase: GetSessionsUseCaseImpl
    ): GetSessionsUseCase

    @Binds
    abstract fun bindDeleteAllSessionsUseCase(
        deleteAllSessionsUseCase: DeleteAllSessionsUseCaseImpl
    ): DeleteAllSessionsUseCase
}