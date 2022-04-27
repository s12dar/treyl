package com.lyvetech.transnature.features.feed.domain.usecase

import com.lyvetech.transnature.features.feed.domain.model.Trail

interface GetFavTrailsUseCase {
    suspend operator fun invoke(): List<Trail>
}