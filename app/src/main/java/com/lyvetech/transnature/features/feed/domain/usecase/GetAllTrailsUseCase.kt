package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTrailsUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Trail>>> {
        return feedRepository.getAllTrails()
    }
}