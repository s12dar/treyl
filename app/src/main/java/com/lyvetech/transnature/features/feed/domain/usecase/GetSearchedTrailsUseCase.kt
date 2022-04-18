package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchedTrailsUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    suspend operator fun invoke(name: String): Flow<Resource<List<Trail>>> {
        if (name.isBlank()) {
            return flow { }
        }

        return feedRepository.getSearchedTrails(name)
    }
}