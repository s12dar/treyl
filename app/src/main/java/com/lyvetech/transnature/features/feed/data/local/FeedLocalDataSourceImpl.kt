package com.lyvetech.transnature.features.feed.data.local

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.features.feed.data.local.dao.FeedDao
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedLocalDataSourceImpl @Inject constructor(
    private val feedDao: FeedDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FeedLocalDataSource {
    override suspend fun insertTrails(trails: List<TrailEntity>) =
        withContext(ioDispatcher) {
            feedDao.insertTrails(trails = trails)
        }

    override suspend fun updateTrail(trail: TrailEntity) =
        withContext(ioDispatcher) {
            feedDao.updateTrail(trail = trail)
        }

    override suspend fun getTrailById(trailId: String): TrailEntity? =
        withContext(ioDispatcher) {
            return@withContext feedDao.getTrailById(trailId)
        }

    override suspend fun deleteTrails(trails: List<String>) =
        withContext(ioDispatcher) {
            feedDao.deleteTrails(trails = trails)
        }

    override suspend fun getSearchedTrails(trail: String): List<TrailEntity> =
        withContext(ioDispatcher) {
            return@withContext feedDao.getSearchedTrails(trail = trail)
        }

    override suspend fun getFavoriteTrails(): List<TrailEntity> =
        withContext(ioDispatcher) {
            return@withContext feedDao.getFavoriteTrails()
        }

    override suspend fun getAllTrails(): List<TrailEntity> =
        withContext(ioDispatcher) {
            return@withContext feedDao.getAllTrails()
        }
}