package com.lyvetech.transnature.features.feed.data.repository

import android.util.Log
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
//    override suspend fun getSearchedTrails(name: String): Flow<Resource<List<Trail>>> = flow {
//        emit(Resource.Loading())
//
//        val trails = dao.getSearchedTrails(name).map { it.toTrail() }
//        emit(Resource.Loading(data = trails))
//
//        try {
//            val remoteTrails = api.getSearchedTrails(name)
//            remoteTrails.map { it.name }.let { dao.deleteTrails(it) }
//            dao.insertTrails(remoteTrails.map { it.toTrailEntity() })
//        } catch (e: HttpException) {
//            emit(
//                Resource.Error(
//                    message = "Oops, something went wrong!",
//                    data = trails
//                )
//            )
//        } catch (e: IOException) {
//            emit(
//                Resource.Error(
//                    message = "Couldn't reach server, check your internet connection.",
//                    data = trails
//                )
//            )
//        }
//
//        val newTrails = dao.getSearchedTrails(name).map { it.toTrail() }
//        emit(Resource.Success(newTrails))
//    }
//
//    override suspend fun getAllTrails(): Flow<Resource<List<Trail>>> = flow {
//        emit(Resource.Loading())
//
//        val trails = dao.getAllTrails().map { it.toTrail() }
//        emit(Resource.Loading(data = trails))
//
//        try {
//            val remoteTrails = api.getAllTrails()
//            remoteTrails.map { it.name }.let { dao.deleteTrails(it) }
//            dao.insertTrails(remoteTrails.map { it.toTrailEntity() })
//        } catch (e: HttpException) {
//            emit(
//                Resource.Error(
//                    message = "Oops, something went wrong!",
//                    data = trails
//                )
//            )
//        } catch (e: IOException) {
//            emit(
//                Resource.Error(
//                    message = "Couldn't reach server, check your internet connection.",
//                    data = trails
//                )
//            )
//        }
//
//        val newTrails = dao.getAllTrails().map { it.toTrail() }
//        emit(Resource.Success(newTrails))
//    }

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
}