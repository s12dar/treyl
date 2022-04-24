package com.lyvetech.transnature.features.feed.data.repository

import com.lyvetech.transnature.core.util.Resource
import com.lyvetech.transnature.core.util.SimpleResource
import com.lyvetech.transnature.features.feed.domain.model.Trail

interface FeedRepository {

    suspend fun getAllTrails(): Resource<List<Trail>>

    suspend fun updateTrail(trail: Trail)
}