package com.lyvetech.transnature.features.tracking.data.local.dao

import androidx.room.*
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(sessionEntity: SessionEntity)

    @Delete
    suspend fun deleteSession(sessionEntity: SessionEntity)

    @Query("DELETE FROM session_table")
    suspend fun deleteAllSessions()

    @Query("SELECT * FROM session_table")
    suspend fun getAllSessions(): List<SessionEntity>
}