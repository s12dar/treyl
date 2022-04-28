package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.repository.FeedRepository
import com.lyvetech.transnature.features.feed.di.IoDispatcher
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTrailsUseCaseImpl @Inject constructor(
    private val feedRepository: FeedRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetTrailsUseCase {
    override suspend operator fun invoke(): Resource<List<Trail>> =
        withContext(ioDispatcher) {
            when (val result = feedRepository.getAllTrails()) {
                is Resource.Success -> {
                    Resource.Success(data = result.data)
                }
                is Resource.Error -> {
                    Resource.Error(data = result.data, result.message)
                }
                else -> {
                    Resource.Loading()
                }
            }
        }
}