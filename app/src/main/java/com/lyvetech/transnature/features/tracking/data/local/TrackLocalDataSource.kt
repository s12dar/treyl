package com.lyvetech.transnature.features.tracking.data.local

import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

interface TrackLocalDataSource {

    suspend fun insertSession(sessionEntity: SessionEntity)

    fun deleteSession(sessionEntity: SessionEntity)
}