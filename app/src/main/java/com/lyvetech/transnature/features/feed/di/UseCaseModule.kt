package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.features.feed.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindTrailsUseCaseImpl(
        getTrailsUseCaseImpl: GetTrailsUseCaseImpl
    ): GetTrailsUseCase

    @Binds
    abstract fun bindSearchedTrailsUseCaseImpl(
        getSearchedUseCaseImpl: GetSearchedTrailsUseCaseImpl
    ): GetSearchedTrailsUseCase

    @Binds
    abstract fun bindGetFavTrailsUseCaseImpl(
        getFavUseCaseImpl: GetFavTrailsUseCaseImpl
    ): GetFavTrailsUseCase

}
