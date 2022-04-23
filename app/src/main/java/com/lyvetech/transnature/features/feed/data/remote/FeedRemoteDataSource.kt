package com.lyvetech.transnature.features.feed.data.remote

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.features.feed.data.remote.dto.TrailDto

interface FeedRemoteDataSource {

    suspend fun getAllTrails(): Resource<List<TrailDto>>

    suspend fun getSearchedTrails(name: String): Resource<List<TrailDto>>
}