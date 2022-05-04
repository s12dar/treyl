package com.lyvetech.transnature.features.tracking.data.local

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.features.tracking.data.local.dao.TrackDao
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackLocalDataSourceImpl @Inject constructor(
    private val trackDao: TrackDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TrackLocalDataSource {
    override suspend fun insertSession(sessionEntity: SessionEntity) =
        withContext(ioDispatcher) {
            trackDao.insertSession(sessionEntity = sessionEntity)
        }


    override suspend fun deleteSession(sessionEntity: SessionEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessions(): List<SessionEntity> =
        withContext(ioDispatcher) {
            return@withContext trackDao.getAllSessions()
        }
}