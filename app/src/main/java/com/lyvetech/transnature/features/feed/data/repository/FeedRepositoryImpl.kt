package com.lyvetech.transnature.features.feed.data.repository

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.local.FeedLocalDataSource
import com.lyvetech.transnature.features.feed.data.remote.FeedRemoteDataSource
import com.lyvetech.transnature.features.feed.di.IoDispatcher
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FeedRepository {

    override suspend fun getAllTrails(): Resource<List<Trail>> =
        withContext(ioDispatcher) {
            when (val result = remoteDataSource.getAllTrails()) {
                is Resource.Success -> {
                    val trails = result.data
                    if (trails != null) {
                        trails.map { it.name }.let { localDataSource.deleteTrails(it) }
                        localDataSource.insertTrails(trails.map { it.toTrailEntity() })
                        val newTrails = localDataSource.getAllTrails().map { it.toTrail() }
                        Resource.Success(newTrails)
                    } else {
                        Resource.Success(null)
                    }
                }
                is Resource.Error -> {
                    val oldTrails = localDataSource.getAllTrails().map { it.toTrail() }
                    Resource.Error(oldTrails, result.message)
                }
                else -> {
                    Resource.Loading()
                }
            }
        }

    override suspend fun updateTrail(trail: Trail) = withContext(ioDispatcher) {
        localDataSource.updateTrail(trail.toTrailEntity())
    }
}