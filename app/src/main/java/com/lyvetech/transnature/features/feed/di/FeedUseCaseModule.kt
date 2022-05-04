package com.lyvetech.transnature.features.feed.di

import com.lyvetech.transnature.features.feed.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedUseCaseModule {

    @Binds
    abstract fun bindTrailsUseCase(
        getTrailsUseCaseImpl: GetTrailsUseCaseImpl
    ): GetTrailsUseCase

    @Binds
    abstract fun bindSearchedTrailsUseCase(
        getSearchedUseCaseImpl: GetSearchedTrailsUseCaseImpl
    ): GetSearchedTrailsUseCase

    @Binds
    abstract fun bindFavTrailsUseCase(
        getFavUseCaseImpl: GetFavTrailsUseCaseImpl
    ): GetFavTrailsUseCase

}
