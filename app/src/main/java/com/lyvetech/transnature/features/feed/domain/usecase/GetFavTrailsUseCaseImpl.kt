package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavTrailsUseCaseImpl @Inject constructor(
    private val feedRepository: FeedRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetFavTrailsUseCase {

    override suspend fun invoke(): List<Trail> =
        withContext(ioDispatcher) {
            feedRepository.getFavTrails()
        }
}