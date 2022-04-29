package com.lyvetech.transnature.features.tracking.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(sessionEntity: SessionEntity)

    @Delete
    fun deleteSession(sessionEntity: SessionEntity)
}