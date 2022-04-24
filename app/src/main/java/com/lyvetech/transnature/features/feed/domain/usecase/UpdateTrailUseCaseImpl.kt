package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.di.IoDispatcher
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateTrailUseCaseImpl @Inject constructor(
    private val feedRepository: FeedRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UpdateTrailUseCase {

    override suspend fun invoke(trail: Trail) =
        withContext(ioDispatcher) {
            feedRepository.updateTrail(trail)
        }
}