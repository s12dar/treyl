package com.lyvetech.transnature.features.tracking.domain.usecase

import com.lyvetech.transnature.features.tracking.domain.model.Session

interface GetSessionsUseCase {
    suspend operator fun invoke(): List<Session>
}