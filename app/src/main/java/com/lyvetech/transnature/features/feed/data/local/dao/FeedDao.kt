package com.lyvetech.transnature.features.feed.data.local.dao

import androidx.room.*
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity

@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrails(trails: List<TrailEntity>)

    @Update
    suspend fun updateTrail(trail: TrailEntity)

    @Query("DELETE FROM TrailEntity WHERE name IN(:trails)")
    suspend fun deleteTrails(trails: List<String>)

    @Query("SELECT * FROM TrailEntity WHERE name LIKE '%' || :trail || '%'")
    suspend fun getSearchedTrails(trail: String): List<TrailEntity>

    @Query("SELECT * FROM TrailEntity WHERE isFav = 1")
    suspend fun getFavoriteTrails(): List<TrailEntity>

    @Query("SELECT * FROM TrailEntity")
    suspend fun getAllTrails(): List<TrailEntity>
}