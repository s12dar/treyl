package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.features.feed.domain.model.Trail

interface UpdateTrailUseCase {
    suspend operator fun invoke(trail: Trail)
}