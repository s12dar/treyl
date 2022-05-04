package com.lyvetech.transnature.features.tracking.data.local.dao

import androidx.room.*
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(sessionEntity: SessionEntity)

    @Delete
    fun deleteSession(sessionEntity: SessionEntity)

    @Query("SELECT * FROM session_table")
    suspend fun getAllSessions(): List<SessionEntity>
}