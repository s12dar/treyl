package com.lyvetech.transnature.features.feed.data.local

import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity

interface FeedLocalDataSource {

    suspend fun insertTrails(trails: List<TrailEntity>)

    suspend fun updateTrail(trail: TrailEntity)

    suspend fun getTrailById(trailId: String): TrailEntity?

    suspend fun deleteTrails(trails: List<String>)

    suspend fun getSearchedTrails(trail: String): List<TrailEntity>

    suspend fun getFavoriteTrails(): List<TrailEntity>

    suspend fun getAllTrails(): List<TrailEntity>
}