package com.lyvetech.transnature.features.tracking.data.local

import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

interface TrackLocalDataSource {

    suspend fun insertSession(sessionEntity: SessionEntity)

    suspend fun deleteSession(sessionEntity: SessionEntity)

    suspend fun deleteAllSessions()

    suspend fun getAllSessions(): List<SessionEntity>
}