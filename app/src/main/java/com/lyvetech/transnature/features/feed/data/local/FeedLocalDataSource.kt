package com.lyvetech.transnature.features.feed.data.local

import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor(
    private val feedDao: FeedDao
) {

    suspend fun insertTrails(trails: List<TrailEntity>) = feedDao.insertTrails(trails)

    suspend fun deleteTrails(trails: List<String>) = feedDao.deleteTrails(trails)

    suspend fun getSearchedTrails(trail: String) = feedDao.getSearchedTrails(trail)

    suspend fun getAllTrails() = feedDao.getAllTrails()
}