package com.lyvetech.transnature.features.feed.data.remote

import com.lyvetech.transnature.features.feed.data.remote.dto.TrailDto
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val feedApiService: FeedApiService
) {

    suspend fun getAllTrails(): List<TrailDto> = feedApiService.getAllTrails()

    suspend fun getSearchedTrails(name: String) = feedApiService.getSearchedTrails(name)
}