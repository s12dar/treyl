package com.lyvetech.transnature.features.tracking.data.repository

import com.lyvetech.transnature.features.tracking.domain.model.Session

interface TrackRepository {

    suspend fun insertSession(session: Session)

    suspend fun getAllSessions(): List<Session>

    suspend fun deleteAllSessions()
}