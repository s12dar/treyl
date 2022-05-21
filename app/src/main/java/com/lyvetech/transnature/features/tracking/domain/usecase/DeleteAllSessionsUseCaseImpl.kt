package com.lyvetech.transnature.features.tracking.domain.usecase

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.features.tracking.data.repository.TrackRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAllSessionsUseCaseImpl @Inject constructor(
    private val trackRepository: TrackRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DeleteAllSessionsUseCase {

    override suspend fun invoke() {
        withContext(ioDispatcher) {
            trackRepository.deleteAllSessions()
        }
    }
}