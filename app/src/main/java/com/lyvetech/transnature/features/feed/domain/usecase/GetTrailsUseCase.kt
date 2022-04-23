package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.domain.model.Trail

interface GetTrailsUseCase {
    suspend operator fun invoke(): Resource<List<Trail>>
}