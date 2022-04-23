package com.lyvetech.transnature.features.feed.data.remote.dto

import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity

data class TrailDto(
    val name: String,
    val desc: String,
    val imgUrl: String,
    val location: String,
    val startLatitude: Double,
    val startLongitude: Double,
    val endLatitude: Double,
    val endLongitude: Double,
    val distanceInMeters: Int,
    val averageTimeInMillis: Long,
    val difficultyLevel: String
) {
    fun toTrailEntity(): TrailEntity {
        return TrailEntity(
            name = name, desc = desc, location = location,
            imgUrl = imgUrl, startLatitude = startLatitude,
            startLongitude = startLongitude, endLatitude = endLatitude,
            endLongitude = endLongitude, distanceInMeters = distanceInMeters,
            averageTimeInMillis = averageTimeInMillis, difficultyLevel = difficultyLevel
        )
    }
}
