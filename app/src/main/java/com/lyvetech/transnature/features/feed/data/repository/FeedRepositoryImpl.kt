package com.lyvetech.transnature.features.feed.data.repository

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.local.FeedDao
import com.lyvetech.transnature.features.feed.data.remote.FeedApiService
import com.lyvetech.transnature.features.feed.domain.model.Trail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: FeedApiService,
    private val dao: FeedDao,
) : FeedRepository {
    override suspend fun getSearchedTrails(name: String): Flow<Resource<List<Trail>>> = flow {
        emit(Resource.Loading())

        val trails = dao.getSearchedTrails(name).map { it.toTrail() }
        emit(Resource.Loading(data = trails))

        try {
            val remoteTrails = api.getSearchedTrails(name)
            remoteTrails.map { it.name }.let { dao.deleteTrails(it) }
            dao.insertTrails(remoteTrails.map { it.toTrailEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = trails
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = trails
                )
            )
        }

        val newTrails = dao.getSearchedTrails(name).map { it.toTrail() }
        emit(Resource.Success(newTrails))
    }

    override suspend fun getAllTrails(): Flow<Resource<List<Trail>>> = flow {
        emit(Resource.Loading())

        val trails = dao.getAllTrails().map { it.toTrail() }
        emit(Resource.Loading(data = trails))

        try {
            val remoteTrails = api.getAllTrails()
            remoteTrails.map { it.name }.let { dao.deleteTrails(it) }
            dao.insertTrails(remoteTrails.map { it.toTrailEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = trails
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = trails
                )
            )
        }

        val newTrails = dao.getAllTrails().map { it.toTrail() }
        emit(Resource.Success(newTrails))
    }
}