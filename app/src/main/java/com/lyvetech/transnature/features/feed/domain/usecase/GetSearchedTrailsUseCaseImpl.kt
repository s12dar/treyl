package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.domain.model.Trail
import javax.inject.Inject

class GetSearchedTrailsUseCaseImpl @Inject constructor(
    private val feedRepository: FeedRepository
) : GetSearchedTrailsUseCase {
    override suspend fun invoke(name: String): Resource<List<Trail>> {
        TODO("Not yet implemented")
    }
}