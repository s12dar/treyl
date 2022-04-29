package com.lyvetech.transnature.features.feed.data.remote

import com.lyvetech.transnature.core.di.IoDispatcher
import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.remote.dto.TrailDto
import com.lyvetech.transnature.features.feed.data.remote.retrofit.FeedApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRemoteDataSourceImpl @Inject constructor(
    private val apiService: FeedApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FeedRemoteDataSource {
    override suspend fun getAllTrails(): Resource<List<TrailDto>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = apiService.getAllTrails()
                if (result.isSuccessful) {
                    Resource.Success(result.body())
                } else {
                    Resource.Success(null)
                }
            } catch (e: Exception) {
                Resource.Error(null, e.toString())
            }
        }

    override suspend fun getSearchedTrails(name: String): Resource<List<TrailDto>> {
        TODO("Not yet implemented")
    }

}