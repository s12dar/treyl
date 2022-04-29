package com.lyvetech.transnature.features.tracking.domain.usecase

import com.lyvetech.transnature.features.tracking.domain.model.Session

interface InsertSessionUseCase {
    suspend operator fun invoke(session: Session)
}