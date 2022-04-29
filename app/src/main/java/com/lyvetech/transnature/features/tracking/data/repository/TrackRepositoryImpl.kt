package com.lyvetech.transnature.features.tracking.data.repository

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.features.tracking.data.local.TrackLocalDataSource
import com.lyvetech.transnature.features.tracking.domain.model.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val localDataSource: TrackLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TrackRepository {

    override suspend fun insertSession(session: Session) = withContext(ioDispatcher) {
        localDataSource.insertSession(session.toSessionEntity())
    }
}