package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.domain.model.Trail

interface GetSearchedTrailsUseCase {
    suspend operator fun invoke(name: String): Resource<List<Trail>>
}