package com.lyvetech.transnature.features.tracking.domain.usecase

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.features.tracking.data.repository.TrackRepository
import com.lyvetech.transnature.features.tracking.domain.model.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertSessionUseCaseImpl @Inject constructor(
    private val trackRepository: TrackRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : InsertSessionUseCase {

    override suspend fun invoke(session: Session) =
        withContext(ioDispatcher) {
            trackRepository.insertSession(session)
        }
}