package com.lyvetech.transnature.features.feed.data.remote.retrofit

import com.lyvetech.transnature.features.feed.data.remote.dto.TrailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedApiService {

    @GET("{trail}")
    suspend fun getSearchedTrails(
        @Path("trail") trail: String
    ): Response<List<TrailDto>>

    @GET("/trails")
    suspend fun getAllTrails(): Response<List<TrailDto>>

    companion object {
        const val BASE_URL = "https://aqueous-basin-19635.herokuapp.com/"
    }
}